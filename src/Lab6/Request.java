package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * this class is using as packet which client will send to server
 */

public class Request implements Serializable {
    private ClientCommand command;
    private Humanoid human;
    private List<Predmet> baggage = new LinkedList<>();

    public Request() {    }

    public Request(ClientCommand command, Humanoid human, List<Predmet> baggage) {
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

    public Humanoid getHuman() { return human; }

    public List<Predmet> getBaggage() { return baggage; }
}
