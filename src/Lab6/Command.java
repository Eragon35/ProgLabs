package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.util.LinkedList;
import java.util.List;

public class Command {
    private ClientCommand command = null;
    private Humanoid human = null;
    private List<Predmet> baggage = new LinkedList<>();

    public Command(ClientCommand command, Humanoid human, List<Predmet> baggage) {
        this.command = command;
        this.human = human;
        this.baggage = baggage;
    }

    public void setCommand(ClientCommand command) {
        this.command = command;
    }

    public void setHuman(Humanoid human) {
        this.human = human;
    }

    public void setBaggage(List<Predmet> baggage) {
        this.baggage = baggage;
    }

    public ClientCommand getCommand() {
        return command;
    }
}
