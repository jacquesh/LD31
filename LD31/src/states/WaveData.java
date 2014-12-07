package states;

public class WaveData
{
    public static WaveData[] waveData;

    public double[] spawnChance = new double[5];

    static
    {
        waveData = new WaveData[10];
        
        waveData[0] = new WaveData();
        waveData[0].spawnChance[0] = 0.526;
        waveData[0].spawnChance[1] = 0.353;
        waveData[0].spawnChance[2] = 0.106;
        waveData[0].spawnChance[3] = 0.014;
        waveData[0].spawnChance[4] = 0.001;

        waveData[1] = new WaveData();
        waveData[1].spawnChance[0] = 0.392;
        waveData[1].spawnChance[1] = 0.392;
        waveData[1].spawnChance[2] = 0.176;
        waveData[1].spawnChance[3] = 0.036;
        waveData[1].spawnChance[4] = 0.003;

        waveData[2] = new WaveData();
        waveData[2].spawnChance[0] = 0.261;
        waveData[2].spawnChance[1] = 0.389;
        waveData[2].spawnChance[2] = 0.261;
        waveData[2].spawnChance[3] = 0.079;
        waveData[2].spawnChance[4] = 0.011;

        waveData[3] = new WaveData();
        waveData[3].spawnChance[0] = 0.150;
        waveData[3].spawnChance[1] = 0.335;
        waveData[3].spawnChance[2] = 0.335;
        waveData[3].spawnChance[3] = 0.150;
        waveData[3].spawnChance[4] = 0.030;

        waveData[4] = new WaveData();
        waveData[4].spawnChance[0] = 0.074;
        waveData[4].spawnChance[1] = 0.244;
        waveData[4].spawnChance[2] = 0.364;
        waveData[4].spawnChance[3] = 0.244;
        waveData[4].spawnChance[4] = 0.074;

        waveData[5] = new WaveData();
        waveData[5].spawnChance[0] = 0.030;
        waveData[5].spawnChance[1] = 0.150;
        waveData[5].spawnChance[2] = 0.335;
        waveData[5].spawnChance[3] = 0.335;
        waveData[5].spawnChance[4] = 0.150;

        waveData[6] = new WaveData();
        waveData[6].spawnChance[0] = 0.011;
        waveData[6].spawnChance[1] = 0.079;
        waveData[6].spawnChance[2] = 0.261;
        waveData[6].spawnChance[3] = 0.389;
        waveData[6].spawnChance[4] = 0.261;

        waveData[7] = new WaveData();
        waveData[7].spawnChance[0] = 0.003;
        waveData[7].spawnChance[1] = 0.036;
        waveData[7].spawnChance[2] = 0.176;
        waveData[7].spawnChance[3] = 0.392;
        waveData[7].spawnChance[4] = 0.392;

        waveData[8] = new WaveData();
        waveData[8].spawnChance[0] = 0.001;
        waveData[8].spawnChance[1] = 0.014;
        waveData[8].spawnChance[2] = 0.106;
        waveData[8].spawnChance[3] = 0.353;
        waveData[8].spawnChance[4] = 0.526;

        waveData[9] = new WaveData();
        waveData[9].spawnChance[0] = 0.000;
        waveData[9].spawnChance[1] = 0.005;
        waveData[9].spawnChance[2] = 0.059;
        waveData[9].spawnChance[3] = 0.290;
        waveData[9].spawnChance[4] = 0.646;
    }
}
