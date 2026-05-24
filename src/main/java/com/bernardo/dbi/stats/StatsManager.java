package com.bernardo.dbi.stats;

import com.bernardo.dbi.core.system.KiSystem;

public class StatsManager {

    private final Str  str;
    private final Con  con;
    private final Dex  dex;
    private final Spi  spi;
    private final Will will;
    private final Mnd  mnd;

    // KiSystem integrado — usa Spi como storage, Will e Mnd como modificadores
    private final KiSystem kiSystem;

    public StatsManager(int strength, int constitution, int dexterity,
                        int spirit, int willpower, int mind) {
        this.str  = new Str(strength);
        this.con  = new Con(constitution);
        this.dex  = new Dex(dexterity);
        this.spi  = new Spi(spirit);
        this.will = new Will(willpower);
        this.mnd  = new Mnd(mind);
        this.kiSystem = new KiSystem(spi, will, mnd);
    }

    // ── GETTERS ───────────────────────────────────────────────────
    public Str     getStr()      { return str; }
    public Con     getCon()      { return con; }
    public Dex     getDex()      { return dex; }
    public Spi     getSpi()      { return spi; }
    public Will    getWill()     { return will; }
    public Mnd     getMnd()      { return mnd; }
    public KiSystem getKiSystem(){ return kiSystem; }

    // ── TICK ──────────────────────────────────────────────────────
    public void tick() {
        con.tickRegeneration();
        kiSystem.tick(); // regen + drain de boost/voo já dentro do KiSystem
    }

    // ── DANO ──────────────────────────────────────────────────────
    public void dealDamage(int baseDamage) {
        if (baseDamage <= 0) return;
        if (dex.tryDodge()) return;
        int damage = baseDamage + str.getAttackPower();
        damage = dex.reduceDamage(damage);
        double hpPercent = con.getHealthPercentage();
        damage = will.boostUnderPressure(damage, hpPercent);
        con.takeDamage(damage);
    }

    // ── ATAQUE ────────────────────────────────────────────────────
    public boolean canAttack(int cost) { return str.getCurrentAttackPower() >= cost; }
    public void useAttack(int cost) { if (canAttack(cost)) str.useAttack(cost); }

    // ── STAMINA ───────────────────────────────────────────────────
    public boolean canUseStamina(int cost) { return con.getCurrentStamina() >= cost; }
    public void useStamina(int cost) { if (canUseStamina(cost)) con.consumeStamina(cost); }

    // ── KI — delegado ao KiSystem ─────────────────────────────────
    public boolean canUseKi(int cost)  { return kiSystem.canUseKi(cost); }
    public boolean useKi(int cost)     { return kiSystem.useKi(cost); }

    // ── CURA ──────────────────────────────────────────────────────
    public void heal(int amount) { con.heal(amount); }
    public boolean isDead() { return con.getCurrentHealth() <= 0; }

    @Override
    public String toString() {
        return "StatsManager{\n  " + str + "\n  " + con + "\n  " + dex
            + "\n  " + spi + "\n  " + will + "\n  " + mnd
            + "\n  " + kiSystem + "\n}";
    }
}
