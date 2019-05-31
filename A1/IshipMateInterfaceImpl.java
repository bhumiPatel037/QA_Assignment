public class IshipMateInterfaceImpl implements  IShipMate {
    @Override
    public boolean isKnownAddress(Address address)
    {
        boolean isKnown = true;
        try {
            String customer = address!=null?address.customer:"";
            String street =address!=null? address.street:"";
            String city = address!=null?address.city:"";
            String province =address!=null? address.province:"";
            String country = address!=null?address.country:"";
            String postalCode =address!=null? address.postalCode:"";

            if (customer == null || customer == "" || customer.trim().isEmpty()) {
                isKnown = false;
            } else if (street == null || street == "" || street.trim().isEmpty()) {
                isKnown = false;
            } else if (city == null || city == "" || city.trim().isEmpty()) {
                isKnown = false;
            } else if (province == null || province == "" || province.trim().isEmpty()) {
                isKnown = false;
            } else if (country == null || country == "" || country.trim().isEmpty()) {
                isKnown = false;
            } else if (postalCode == null || postalCode == "" || postalCode.trim().isEmpty()) {
                isKnown = false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

         if(isKnown)
         {
             return  true;
         }
         else
         {
             return false;
         }

    }

    @Override
    public String shipToAddress(Address address, int count, String drugName) throws Exception {

        IDatabaseImplementation iDatabaseImplemetation = new IDatabaseImplementation();
        String estimated_date="30-06-2019";

      if(isKnownAddress(address))
      {
          if(iDatabaseImplemetation.isDrugExist(drugName))
          {
              Integer drugCount = iDatabaseImplemetation.drugCount(drugName);
              if(drugCount>=count)
              {
                  return estimated_date;
              }
              else
              {
                  throw new Exception("There is not enough drug count in warehouse");
              }

          }
          else
          {
              throw new Exception("The given Drug is invalid");
          }
      }
      else
      {
          throw new Exception("The given Address is unknown address");
      }


    }
}
