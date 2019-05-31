import java.util.HashMap;
import java.util.Map;

public class IDatabaseImplementation implements IDatabase
{

    HashMap<String, Integer> mockDrugDataBase = new HashMap<String, Integer>();

    public IDatabaseImplementation()
    {
        mockDrugDataBase.put("Vicodin",100);
        mockDrugDataBase.put("Simvastatin",120);
        mockDrugDataBase.put("Lisinopril",90);
        mockDrugDataBase.put("Levothyroxine",20);
        mockDrugDataBase.put("Azithromycin",20);
        mockDrugDataBase.put("Metformin",50);
        mockDrugDataBase.put("Lipitor",102);
        mockDrugDataBase.put("Amlodipine",80);
        mockDrugDataBase.put("Amoxicillin",78);
        mockDrugDataBase.put("Hydrochlorothiazide",10);
        mockDrugDataBase.put("Methamphetamine",90);
        mockDrugDataBase.put("PCP",60);
        mockDrugDataBase.put("LSD",5);
        mockDrugDataBase.put("Marijuana",10);
        mockDrugDataBase.put("Ecstasy",12);

    }

    @Override
    public Integer drugCount(String drugName)
    {
        Integer drug_count=0;
        if(isDrugExist(drugName))
        {
            drug_count=mockDrugDataBase.get(drugName);
            return drug_count;
        }
        else
        {
            return drug_count;
        }

    }

    @Override
    public Boolean isDrugExist(String drugName)
    {

        if (mockDrugDataBase.containsKey(drugName))
        {

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean claimDrug(String drugName,Integer quantity)
    {
        if(isDrugExist(drugName)) {
            if(drugCount(drugName)>=quantity) {
                Integer drugCount = mockDrugDataBase.get(drugName);
                drugCount = drugCount - quantity;
                mockDrugDataBase.replace(drugName, drugCount);
                return true;
            }
            else
            {
                return false;
            }

        }
        else
        {
            return false;
        }
    }

    public void testDrugList()
    {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (Map.Entry<String, Integer> en : mockDrugDataBase.entrySet())
        {
            System.out.println("Drug = " + en.getKey() + ", Count = " +drugCount(en.getKey()) );
        }
    }



}
