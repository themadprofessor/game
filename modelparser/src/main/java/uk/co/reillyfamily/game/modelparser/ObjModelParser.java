package uk.co.reillyfamily.game.modelparser;

import org.joml.Vector3f;
import org.joml.Vector3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.unloaded.UnloadedModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by stuart on 11/01/17.
 */
public class ObjModelParser implements ModelParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjModelParser.class);

    @Override
    public UnloadedModel parse(InputStream in) {
        ArrayList<Vector3f> vertices = new ArrayList<>();
        ArrayList<Vector3i> faces = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty() || (line = line.trim()).isEmpty()) {
                    continue;
                }
                String[] split = line.split(" ");
                switch (split[0]) {
                    case "v":
                        if (split.length != 4) {
                            LOGGER.error("Vertex definition must contain 3 dimensions [{}]", line);
                        } else {
                            vertices.add(toVectorf(Arrays.copyOfRange(split, 1, split.length)));
                        }
                        break;
                    case "f":
                        if (split.length != 4) {
                            LOGGER.error("Face definition must contain 3 values [{}]", line);
                        } else {
                            faces.add(toVectori(Arrays.copyOfRange(split, 1, split.length)));
                        }
                        break;
                    default:
                        LOGGER.warn("Unknown line type [{}]", split[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Successfully loaded {} vertex definitions", vertices.size());
        LOGGER.info("Successfully loaded {} face definitions", faces.size());

        Float[] verts = vertices.stream().flatMap(vec -> Stream.of(vec.x, vec.y, vec.z)).toArray(Float[]::new);
        int[] order = faces.stream().flatMapToInt(vec -> IntStream.of(vec.x, vec.y, vec.z)).toArray();

        return new UnloadedModel(toPrim(verts), order);
    }

    private Vector3f toVectorf(String[] data) {
        return new Vector3f(Float.parseFloat(data[0]), Float.parseFloat(data[1]), Float.parseFloat(data[2]));
    }

    private Vector3i toVectori(String[] data) {
        return new Vector3i(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
    }

    private float[] toPrim(Float[] data) {
        float[] prims = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            prims[i] = data[i];
        }
        return prims;
    }
}
