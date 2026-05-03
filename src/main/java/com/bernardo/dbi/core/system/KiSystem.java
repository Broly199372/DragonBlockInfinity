package com.bernardo.dbi.core.system;
/**
 * Sistema de Ki (Energia) para o mod Dragon Block Infinity
 * Gerencia o consumo e geração de Ki para habilidades especiais
 */
public class KiSystem {
    
    // Custo de Ki base para um ataque de soco
    public static final int KI_SOCO_BASE = 5;
    
    // Custo de Ki para correr/dash
    public static final int KI_RUN = 3;
    
    // Flag que indica se o boost está ativo
    public boolean boostAtivo;
    
    // Custo de Ki por tick (executado a cada frame) quando o boost está ativo
    public static final int custoTick = 8;

    /**
     * Tenta usar/consumir a quantidade especificada de Ki
     * @param amount Quantidade de Ki a ser consumida
     * @return true se havia Ki suficiente e foi consumido, false caso contrário
     */
    public boolean useKi(int amount) {
        // Verifica se há Ki suficiente
        if (currentKi < amount) {
            return false;
        }
        // Consome o Ki
        currentKi -= amount;
        return true;
    }

    
    // Calcula o custo do ataque de soco com multiplicador de transformação
    int custo = (int)(KI_SOCO_BASE * transform.getMultiplier());

    // Se houver Ki suficiente, realiza o ataque de soco
    if (kiSystem.useKi(custo)) {
    }
    
    // Calcula o custo de correr com multiplicador de transformação
    int run = (int)(KI_RUN * transform.getMultiplier());

    // Se houver Ki suficiente, permite correr
    if (kiSystem.useKi(run)) {
    }
    
    // Verifica se o boost está ativo
    if (boostAtivo) {
        // Tenta consumir Ki a cada tick para manter o boost ativo
        if (!useKi(custoTick)) {
            // Se não houver Ki suficiente, desativa o boost
            boostAtivo = false; // acabou o Ki
        }
    }

       
    /**
     * Drena (consome) uma quantidade de Ki a cada tick
     * Usado para efeitos contínuos como voo ou transformações
     * @param amount Quantidade de Ki a ser drenada por tick
     */
    public void drainPerTick(int amount) {
        // Tenta consumir o Ki
        if (!useKi(amount)) {
            // Se não houver Ki, a ação se interrompe
        }
    }

}
