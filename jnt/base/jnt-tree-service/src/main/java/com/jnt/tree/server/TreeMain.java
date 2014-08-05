package com.jnt.tree.server;

import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.node.ContributionLocationHelper;
import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;

public class TreeMain {
    public static void main(String[] args) {
        String contribution = ContributionLocationHelper.getContributionLocation(TreeMain.class);
        final Node node = NodeFactory.newInstance().createNode("META-INF/app-tree-service/server.composite", new Contribution("lenovo", contribution));
        node.start();

        System.out.println("node start.");
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                node.stop();
                System.out.println("node is stop");
            }
        });
        while (true) {
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }
    }
}
