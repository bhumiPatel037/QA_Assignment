import java.util.HashMap;

public class Help
{
	public String getHelp(String command)
	{
		if (command != null && command.length() != 0)
		{
			return createCommandBuilder(command).getCommand();
		}
		return listAllCommands();
	}

	public String listAllCommands()
	{
		return "Commands: print, open, close";
	}

	public CommandClass createCommandBuilder(String commandType)
	{
		CommandClass commandClass ;
		HashMap<String,CommandClass> stringCommandClassHashMap= new HashMap<String,CommandClass>();
		stringCommandClassHashMap.put("print",new Print());
		stringCommandClassHashMap.put("open",new Open());
		stringCommandClassHashMap.put("close",new Close());
		commandClass=stringCommandClassHashMap.get(commandType);
		return commandClass;
	}

}
