package uk.co.reillyfamily.game.modelparser;

import org.joml.Vector2f;
import org.joml.Vector2i;
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
import java.util.Collections;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by stuart on 11/01/17.
 */
public class ObjModelParser implements ModelParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjModelParser.class);

    @Override
    public UnloadedModel parse(InputStream in) {
        List<Vector3f> rawVertices = new ArrayList<>();
        List<Vector2f> rawTextures = new ArrayList<>();
        List<Vector3f> rawNormals = new ArrayList<>();
        List<Vector3i> vertexOrder = new ArrayList<>();
        List<Vector3i> textureOrder = new ArrayList<>();
        List<Vector3i> normalOrder = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty() || (line = line.trim()).isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] split = line.split(" ");
                switch (split[0]) {
                    case "v":
                        if (split.length != 4) {
                            LOGGER.error("Vertex definition must contain 3 dimensions [{}]", line);
                        } else {
                            rawVertices.add(toVectorf(Arrays.copyOfRange(split, 1, split.length)));
                        }
                        break;
                    case "vt":
                        if (split.length != 3) {
                            LOGGER.error("Texture coordinate definition must have 2 dimensions [{}]", line);
                        } else {
                            rawTextures.add(new Vector2f(Float.valueOf(split[1]), Float.valueOf(split[2])));
                        }
                        break;
                    case "vn":
                        if (split.length != 4) {
                            LOGGER.error("Normal definition must contain 3 dimensions [{}]", line);
                        } else {
                            rawNormals.add(toVectorf(Arrays.copyOfRange(split, 1, split.length)));
                        }
                    case "f":
                        if (split.length != 4) {
                            LOGGER.error("Face definition must contain 3 values [{}]", line);
                        } else {
                            if (!split[0].contains("////") && split[0].chars().filter(c -> c=='/').count() != 2) {
                                Vector3i vertex = new Vector3i();
                                Vector3i normal = new Vector3i();
                                Vector3i texture = new Vector3i();
                                for (int i = 0; i < split.length; i++) {
                                    String[] dimension = split[i].split("//");
                                    setComponent(vertex, i, Integer.parseInt(dimension[0]));
                                    setComponent(normal, i, Integer.parseInt(dimension[1]));
                                    setComponent(texture, i, Integer.parseInt(dimension[2]));
                                }
                                vertexOrder.add(vertex);
                                normalOrder.add(normal);
                                textureOrder.add(texture);
                            }
                        }
                        break;
                    default:
                        LOGGER.warn("Unknown line type [{}]", split[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Successfully loaded {} vertex definitions", rawVertices.size());
        LOGGER.info("Successfully loaded {} face definitions", vertexOrder.size());

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();


        Float[] verts = rawVertices.stream().flatMap(vec -> Stream.of(vec.x, vec.y, vec.z)).toArray(Float[]::new);
        int[] order = vertexOrder.stream().flatMapToInt(vec -> IntStream.of(vec.x, vec.y, vec.z)).toArray();

        return new UnloadedModel(toPrim(verts), order);
    }

    private Vector3f toVectorf(String[] data) {
        return new Vector3f(Float.parseFloat(data[0]), Float.parseFloat(data[1]), Float.parseFloat(data[2]));
    }

    private Vector3i toVectori(String[] data) {
        return new Vector3i(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
    }

    private Vector3i setComponent(Vector3i vec, int component, int value) {
        if (component == 0) {
            vec.x = value;
        } else if (component == 1) {
            vec.y = value;
        } else if (component == 2) {
            vec.z = value;
        }
        return vec;
    }

    private float[] toPrim(Float[] data) {
        float[] prims = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            prims[i] = data[i];
        }
        return prims;
    }
}
