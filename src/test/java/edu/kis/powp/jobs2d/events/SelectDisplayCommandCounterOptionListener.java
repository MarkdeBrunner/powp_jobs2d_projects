package edu.kis.powp.jobs2d.events;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.visitor.CommandCounterVisitor;

public class SelectDisplayCommandCounterOptionListener implements ActionListener {
    private final CommandManager commandManager;

    public SelectDisplayCommandCounterOptionListener(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        DriverCommand command = commandManager.getCurrentCommand();
        
        if (command == null) {
            JOptionPane.showMessageDialog(null, "No command loaded.", "Command Counter Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int totalCount = CommandCounterVisitor.countAll(command);
        int operateToCount = CommandCounterVisitor.countOperateTo(command);
        int setPositionCount = CommandCounterVisitor.countSetPosition(command);
        int compoundCount = CommandCounterVisitor.countCompound(command);
        
        String message = String.format(
            "Command Counter Information:\n\n" +
            "Total Commands: %d\n" +
            "OperateTo Commands: %d\n" +
            "SetPosition Commands: %d\n" +
            "Compound Commands: %d",
            totalCount, operateToCount, setPositionCount, compoundCount
        );
        
        JOptionPane.showMessageDialog(null, message, "Command Counter Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
