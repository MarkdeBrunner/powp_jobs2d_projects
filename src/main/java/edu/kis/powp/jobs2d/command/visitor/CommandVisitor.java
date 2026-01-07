package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;


public interface CommandVisitor {
    void visit(SetPositionCommand command);
    void visit(OperateToCommand command);
    void visit(ICompoundCommand command);
}
