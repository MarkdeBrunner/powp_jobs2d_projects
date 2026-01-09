package edu.kis.powp.jobs2d.visitor;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.util.Iterator;

public class CounterVisitor implements CommandVisitor {

    private int count = 0;

    public int getCount() {
        return count;
    }

    public void resetCount() {
        this.count = 0;
    }

    public static int countCommands(DriverCommand command) {
        CounterVisitor visitor = new CounterVisitor();
        command.accept(visitor);
        return visitor.getCount();
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        count++;
    }

    @Override
    public void visit(OperateToCommand operateToCommand) {
        count++;
    }

    @Override
    public void visit(ICompoundCommand iCompoundCommand) {
        Iterator<DriverCommand> iterator = iCompoundCommand.iterator();

        while(iterator.hasNext()) {
            DriverCommand command = iterator.next();
            command.accept(this);
        }
    }
}
