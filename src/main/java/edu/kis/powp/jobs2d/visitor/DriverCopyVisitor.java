package edu.kis.powp.jobs2d.visitor;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Collectors;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.AnimatedDriverDecorator;
import edu.kis.powp.jobs2d.drivers.DriverComposite;
import edu.kis.powp.jobs2d.drivers.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.transformation.TransformerDriverDecorator;

/**
 * Visitor that produces a deep copy of the driver hierarchy.
 */
public class DriverCopyVisitor implements DriverVisitor {

    private VisitableJob2dDriver copy;

    private DriverCopyVisitor() {
    }

    public static VisitableJob2dDriver deepCopy(VisitableJob2dDriver driver) {
        DriverCopyVisitor visitor = new DriverCopyVisitor();
        driver.accept(visitor);
        return visitor.copy;
    }

    @Override
    public void visit(AnimatedDriverDecorator animatedDriverDecorator) {
        VisitableJob2dDriver targetCopy = deepCopy(animatedDriverDecorator.getTargetDriver());
        copy = new AnimatedDriverDecorator(targetCopy, animatedDriverDecorator.getDelayMs());
    }

    @Override
    public void visit(DriverComposite driverComposite) {
        List<VisitableJob2dDriver> copiedDrivers = driverComposite.getDrivers()
                .stream()
                .map(DriverCopyVisitor::deepCopy)
                .collect(Collectors.toList());
        copy = new DriverComposite(copiedDrivers);
    }

    @Override
    public void visit(LoggerDriver loggerDriver) {
        copy = new LoggerDriver();
    }

    @Override
    public void visit(LineDriverAdapter lineDriverAdapter) {
        ILine lineCopy = copyLine(lineDriverAdapter.getLine());
        DrawPanelController controller = lineDriverAdapter.getDrawController();
        LineDriverAdapter adapterCopy = new LineDriverAdapter(controller, lineCopy, lineDriverAdapter.getName());
        adapterCopy.setPosition(lineDriverAdapter.getStartX(), lineDriverAdapter.getStartY());
        copy = adapterCopy;
    }

    @Override
    public void visit(TransformerDriverDecorator transformerDriverDecorator) {
        Job2dDriver innerDriver = transformerDriverDecorator.getDriver();
        Job2dDriver innerCopy = innerDriver instanceof VisitableJob2dDriver
                ? deepCopy((VisitableJob2dDriver) innerDriver)
                : innerDriver;
        copy = new TransformerDriverDecorator(innerCopy, transformerDriverDecorator.getStrategy());
    }

    private ILine copyLine(ILine line) {
        try {
            Constructor<? extends ILine> constructor = line.getClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            return line;
        }
    }
}
