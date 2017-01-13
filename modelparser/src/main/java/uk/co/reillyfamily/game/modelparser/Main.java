package uk.co.reillyfamily.game.modelparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;

/**
 * Created by stuart on 10/01/17.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        /*if (args.length != 1) {
            throw new IllegalStateException("Please specify a model file!");
        }
        File file = new File(args[0]);*/
        File file = new File("teapot.obj");
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName() + " does not exist!");
        } else if (!file.isFile()) {
            throw new IOException(file.getName() + " is not a file!");
        }

        ObjModelParser objModelParser = new ObjModelParser();

        File outFile = new File(file.getParent(), file.getName().substring(0, file.getName().length()-3) + "model");
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(outFile)))) {
            out.writeObject(objModelParser.parse(Files.newInputStream(file.toPath())));
            out.flush();
        }

        LOGGER.info("Finished");
    }


}
