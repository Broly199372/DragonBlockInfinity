package com.bernardo.dbi.core.system;

import com.bernardo.dbi.stats.Mnd;
import com.bernardo.dbi.stats.Spi;
import com.bernardo.dbi.stats.Will;

/**
 * Sistema central de Ki do Dragon Block Infinity.
 *
 * Integração com stats:
 *   Spi  → armazena e regenera Ki (getMaxKi, getCurrentKi, getKiRegenRate)
 *   Will → reduz custo de Ki de habilidades (reduceKiCost)
 *   Mnd  → amplifica taxa de regeneração (boostRegen) e reduz desperdício (reduceWaste)
 *
 * Fluxo por tick:
 *   1. Se boost ativo → drena CUSTO_BOOST_TICK por tick
 *   2. Regeneração passiva: maxKi * regenRate, amplificado por Mnd
 *   3. Se Ki chegar a 0 com boost ativo → boost desativa automaticamente
 */
public class KiSystem {

    // ── CUSTOS BASE ───────────────────────────────────────────────
    public static final int CUSTO_SOCO        = 5;
    public static final int CUSTO_KI_BLAST    = 30;
    public static final int CUSTO_KAMEHAMEHA  = 150;
    public static final int CUSTO_RUN         = 3;
    public static final int CUSTO_VOO_TICK    = 2;
    public static final int CUSTO_BOOST_TICK  = 8;
    public static final int CUSTO_TRANSFORM   = 50;

    // ── MULTIPLICADORES DE TRANSFORMAÇÃO ─────────────────────────
    public static final float MULT_BASE       = 1.0f;
    public static final float MULT_SSJ1       = 1.5f;
    public static final float MULT_SSJ2       = 2.0f;
    public static final float MULT_SSJ3       = 4.0f;
    public static final float MULT_SSJ_BLUE   = 3.0f;
    public static final float MULT_KAIOKEN    = 2.5f;

    // ── ESTADO ────────────────────────────────────────────────────
    private boolean boostAtivo = false;
    private boolean voandoAtivo = false;
    private float transformMultiplier = MULT_BASE;

    // ── REFERÊNCIAS AOS STATS ─────────────────────────────────────
    private final Spi spi;
    private final Will will;
    private final Mnd mnd;

    public KiSystem(Spi spi, Will will, Mnd mnd) {
        this.spi  = spi;
        this.will = will;
        this.mnd  = mnd;
    }

    // ── TICK ──────────────────────────────────────────────────────

    /**
     * Chamado a cada tick do player.
     * Drena Ki contínuo se boost/voo ativo.
     * Regenera Ki passivamente.
     */
    public void tick() {
        // Drena por ações contínuas
        if (boostAtivo) {
            int custo = calcCusto(CUSTO_BOOST_TICK);
            if (!spi.hasKi(custo)) {
                boostAtivo = false;
            } else {
                spi.useKi(custo);
            }
        }

        if (voandoAtivo) {
            int custo = calcCusto(CUSTO_VOO_TICK);
            if (!spi.hasKi(custo)) {
                voandoAtivo = false;
            } else {
                spi.useKi(custo);
            }
        }

        // Regeneração passiva
        int regen = (int)(spi.getMaxKi() * spi.getKiRegenRate());
        regen = mnd.boostRegen(regen);
        // Regen mais lento durante boost ou voo
        if (boostAtivo || voandoAtivo) regen = Math.max(1, regen / 3);
        spi.restoreKi(regen);
    }

    // ── USO DE KI ─────────────────────────────────────────────────

    /**
     * Tenta usar Ki para uma ação.
     * Aplica redução de Will e transformação.
     * @return true se havia Ki suficiente e foi consumido
     */
    public boolean useKi(int custoBase) {
        int custo = calcCusto(custoBase);
        if (!spi.hasKi(custo)) return false;
        spi.useKi(custo);
        return true;
    }

    /** Verifica se há Ki suficiente para uma ação sem consumir. */
    public boolean canUseKi(int custoBase) {
        return spi.hasKi(calcCusto(custoBase));
    }

    /** Drena Ki sem verificação — use apenas quando já validado. */
    public void forceUseKi(int amount) {
        spi.useKi(amount);
    }

    /** Restaura Ki (ex: ao absorver energia, senzu bean). */
    public void restoreKi(int amount) {
        spi.restoreKi(amount);
    }

    /** Restaura Ki para o máximo instantaneamente. */
    public void fullRestore() {
        spi.restoreKi(spi.getMaxKi());
    }

    // ── AÇÕES ESPECÍFICAS ─────────────────────────────────────────

    public boolean usarSoco()       { return useKi(CUSTO_SOCO); }
    public boolean usarKiBlast()    { return useKi(CUSTO_KI_BLAST); }
    public boolean usarKamehameha() { return useKi(CUSTO_KAMEHAMEHA); }
    public boolean usarRun()        { return useKi(CUSTO_RUN); }

    public boolean usarTransform() {
        if (!canUseKi(CUSTO_TRANSFORM)) return false;
        spi.useKi(calcCusto(CUSTO_TRANSFORM));
        return true;
    }

    // ── BOOST ─────────────────────────────────────────────────────

    public void ativarBoost() {
        if (canUseKi(CUSTO_BOOST_TICK)) boostAtivo = true;
    }

    public void desativarBoost() {
        boostAtivo = false;
    }

    public boolean isBoostAtivo() { return boostAtivo; }

    // ── VOO ───────────────────────────────────────────────────────

    public void ativarVoo() {
        if (canUseKi(CUSTO_VOO_TICK)) voandoAtivo = true;
    }

    public void desativarVoo() {
        voandoAtivo = false;
    }

    public boolean isVoandoAtivo() { return voandoAtivo; }

    // ── TRANSFORMAÇÃO ─────────────────────────────────────────────

    public void setTransformMultiplier(float mult) {
        this.transformMultiplier = mult;
    }

    public float getTransformMultiplier() { return transformMultiplier; }

    // ── GETTERS DE ESTADO ─────────────────────────────────────────

    public int getCurrentKi()  { return spi.getCurrentKi(); }
    public int getMaxKi()      { return spi.getMaxKi(); }
    public float getKiPercent(){ return (float) spi.getCurrentKi() / spi.getMaxKi(); }
    public boolean isKiEmpty() { return spi.getCurrentKi() <= 0; }
    public boolean isKiFull()  { return spi.getCurrentKi() >= spi.getMaxKi(); }

    // ── CÁLCULO DE CUSTO ─────────────────────────────────────────

    /**
     * Calcula o custo final de uma ação.
     * Ordem: base → Will reduz → Mnd reduz desperdício → multiplicador de transformação
     */
    private int calcCusto(int base) {
        int custo = will.reduceKiCost(base);
        custo = mnd.reduceWaste(custo);
        custo = (int)(custo * transformMultiplier);
        return Math.max(1, custo);
    }

    @Override
    public String toString() {
        return "KiSystem{ki=" + getCurrentKi() + "/" + getMaxKi()
            + ", boost=" + boostAtivo
            + ", voo=" + voandoAtivo
            + ", mult=" + transformMultiplier + "}";
    }
}
