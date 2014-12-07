package states;

public class WaveData
{
    public static WaveData[] waveData;

    public double[] spawnChance = new double[4];

    static
    {
        waveData = new WaveData[10];
        
        waveData[0] = new WaveData();
        waveData[0].spawnChance[0] = 0.527;
        waveData[0].spawnChance[1] = 0.353;
        waveData[0].spawnChance[2] = 0.106;
        waveData[0].spawnChance[3] = 0.014;

        waveData[1] = new WaveData();
        waveData[1].spawnChance[0] = 0.392;
        waveData[1].spawnChance[1] = 0.395;
        waveData[1].spawnChance[2] = 0.176;
        waveData[1].spawnChance[3] = 0.036;

        waveData[2] = new WaveData();
        waveData[2].spawnChance[0] = 0.261;
        waveData[2].spawnChance[1] = 0.393;
        waveData[2].spawnChance[2] = 0.268;
        waveData[2].spawnChance[3] = 0.079;

        waveData[3] = new WaveData();
        waveData[3].spawnChance[0] = 0.155;
        waveData[3].spawnChance[1] = 0.340;
        waveData[3].spawnChance[2] = 0.350;
        waveData[3].spawnChance[3] = 0.155;

        waveData[4] = new WaveData();
        waveData[4].spawnChance[0] = 0.104;
        waveData[4].spawnChance[1] = 0.264;
        waveData[4].spawnChance[2] = 0.354;
        waveData[4].spawnChance[3] = 0.274;

        waveData[5] = new WaveData();
        waveData[5].spawnChance[0] = 0.090;
        waveData[5].spawnChance[1] = 0.190;
        waveData[5].spawnChance[2] = 0.345;
        waveData[5].spawnChance[3] = 0.350;

        waveData[6] = new WaveData();
        waveData[6].spawnChance[0] = 0.011;
        waveData[6].spawnChance[1] = 0.139;
        waveData[6].spawnChance[2] = 0.361;
        waveData[6].spawnChance[3] = 0.490;

        waveData[7] = new WaveData();
        waveData[7].spawnChance[0] = 0.053;
        waveData[7].spawnChance[1] = 0.128;
        waveData[7].spawnChance[2] = 0.326;
        waveData[7].spawnChance[3] = 0.492;

        waveData[8] = new WaveData();
        waveData[8].spawnChance[0] = 0.001;
        waveData[8].spawnChance[1] = 0.114;
        waveData[8].spawnChance[2] = 0.332;
        waveData[8].spawnChance[3] = 0.553;

        waveData[9] = new WaveData();
        waveData[9].spawnChance[0] = 0.000;
        waveData[9].spawnChance[1] = 0.055;
        waveData[9].spawnChance[2] = 0.309;
        waveData[9].spawnChance[3] = 0.636;
    }
}
