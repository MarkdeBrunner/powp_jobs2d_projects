package edu.kis.powp.jobs2d.visitor;

import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

public interface CommandVisitor {
    void visit(SetPositionCommand setPositionCommand);
    void visit(OperateToCommand operateToCommand);

    void visit(ICompoundCommand iCompoundCommand);
}
