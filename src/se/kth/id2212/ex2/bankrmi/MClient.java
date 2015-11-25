package se.kth.id2212.ex2.bankrmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.StringTokenizer;

public class MClient {
    //private static final String USAGE = "java bankrmi.MClient <market_url>";
    private static final String USAGE = "java MClient <market_rmi_url>";
    private static final String DEFAULT_MARKET_NAME = "JMart";
    TraderAcc traderacc; //TODO: Change name to TraderAcc
    Market marketobj; //TODO: Change name to Market
    private String marketname;
    String mclientname;

    //enum lists of commands available
    static enum CommandName {
        sell, buy, wish, listProducts, listTraderAccs, newTraderAcc, getTraderAcc, deleteTraderAcc, quit, help;
        //        newAccount, getAccount, deleteAccount, deposit, withdraw, balance, quit, help, list;
    };

    //Konstruktor
    public MClient(String marketName) {
        this.marketname = marketName;
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            marketobj = (Market) Naming.lookup(marketname); //TODO: Change name to Market
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to market: " + marketname);
    }

    public MClient() {
        this(DEFAULT_MARKET_NAME);
    }

    public void run() {
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(mclientname + "@" + marketname + ">");
            try {
                String userInput = consoleIn.readLine();
                execute(parse(userInput));
            } catch (RejectedException re) {
                System.out.println(re);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: Fix the commands
    private Command parse(String userInput) {
        if (userInput == null) {
            return null;
        }

        StringTokenizer tokenizer = new StringTokenizer(userInput);
        if (tokenizer.countTokens() == 0) {
            return null;
        }

        CommandName commandName = null;
        String userName = null;
        float amount = 0;
        int userInputTokenNo = 1;

        while (tokenizer.hasMoreTokens()) {
            switch (userInputTokenNo) {
                case 1:
                    try {
                        String commandNameString = tokenizer.nextToken();
                        commandName = CommandName.valueOf(CommandName.class, commandNameString);
                    } catch (IllegalArgumentException commandDoesNotExist) {
                        System.out.println("Illegal command");
                        return null;
                    }
                    break;
                case 2:
                    userName = tokenizer.nextToken();
                    break;
                case 3:
                    try {
                        amount = Float.parseFloat(tokenizer.nextToken());
                    } catch (NumberFormatException e) {
                        System.out.println("Illegal amount");
                        return null;
                    }
                    break;
                default:
                    System.out.println("Illegal command");
                    return null;
            }
            userInputTokenNo++;
        }
        return new Command(commandName, userName, amount);
    }

    //TODO: Fix the commands.
    void execute(Command command) throws RemoteException, RejectedException {
        if (command == null) {
            return;
        }

        switch (command.getCommandName()) {
            case listTraderAccs: //TODO: Rename from list to listTraderAccs
                try {
                    for (String accountHolder : marketobj.listTraderAccs()) {
                        System.out.println(accountHolder);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                return;
            case quit:
                System.exit(0);
            case help:
                for (CommandName commandName : CommandName.values()) {
                    System.out.println(commandName);
                }
                return;
                        
        }

        //TODO: Fix the commands.
        // all further commands require a name to be specified
        String userName = command.getUserName();
        if (userName == null) {
            userName = mclientname;
        }

        if (userName == null) {
            System.out.println("name is not specified");
            return;
        }

        switch (command.getCommandName()) {
            case newTraderAcc:
                mclientname = userName;
                marketobj.newTraderAcc(userName);
                return;
            case deleteTraderAcc:
                mclientname = userName;
                marketobj.deleteTraderAcc(userName);
                return;
        }

        //TODO: Fix the commands
        // all further commands require a Account reference
        TraderAcc acc = marketobj.getTraderAcc(userName); //TODO: Change name from Account to TraderAcc
        if (acc == null) {
            System.out.println("No account for " + userName);
            return;
        } else {
            traderacc = acc;
            mclientname = userName;
        }

        switch (command.getCommandName()) {
            case getTraderAcc:
                System.out.println(traderacc);
                break;
            case sell:
                traderacc.sell(userName, 0);
                //traderacc.sell(command.getAmount());
                break;
            case buy:
                traderacc.buy(userName, 0);
                //traderacc.buy(command.getAmount());
                break;
            /*case balance:
                System.out.println("balance: $" + traderacc.getBalance());
                break;
            */
            default:
                System.out.println("Illegal command");
        }
    }

    //TODO: Fix the commands
    private class Command {
        private String userName;
        private float amount;
        private CommandName commandName;

        private String getUserName() {
            return userName;
        }

        private float getAmount() {
            return amount;
        }

        private CommandName getCommandName() {
            return commandName;
        }

        private Command(MClient.CommandName commandName, String userName, float amount) {
            this.commandName = commandName;
            this.userName = userName;
            this.amount = amount;
        }
    }

    public static void main(String[] args) {
        if ((args.length > 1) || (args.length > 0 && args[0].equals("-h"))) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String marketName;
        if (args.length > 0) {
            marketName = args[0];
            new MClient(marketName).run();
        } else {
            new MClient().run();
        }
    }
}
