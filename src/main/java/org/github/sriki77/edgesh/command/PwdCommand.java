package org.github.sriki77.edgesh.command;

import org.github.sriki77.edgesh.data.ContextNode;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.command.ShellCommand.PWD;

@EdgeMgmtCommand
public class PwdCommand implements Command {
    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if(command!= PWD){
            return false;
        }
        printPwd(context.currentNode(), out);
        out.println();
        return true;
    }

    private void printPwd(ContextNode node, PrintWriter out) {
        if(node==null){
            return;
        }
        printPwd(node.parent(),out);
        out.print(node+" ");
    }

    @Override
    public EdgeEntity applicableTo() {
        return EdgeEntity.ALL;
    }
}
