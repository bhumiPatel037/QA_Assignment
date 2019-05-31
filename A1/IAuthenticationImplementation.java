public class IAuthenticationImplementation implements IAuthentication
{
    @Override
    public boolean authenticate(String apiKey) {

        boolean isAuthenticate = apiKey.toLowerCase().contains("true");
        boolean notAuthenticate=apiKey.toLowerCase().contains("false");

        if(isAuthenticate)
        {
           return true;
        }
        else if(notAuthenticate || apiKey==null || apiKey.contains("") || apiKey.trim().isEmpty())
        {
            return false;
        }
        else
        {
            return false;
        }



    }

    @Override
    public boolean authorize(String username, RequestAction action) {

      RequestAction actionType= action;
      if(username.toLowerCase().contains(actionType.name().toLowerCase()))
      {
          return true;
      }
      else
      {
          return false;
      }


    }
}
