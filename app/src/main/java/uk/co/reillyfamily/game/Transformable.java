package uk.co.reillyfamily.game;

import org.joml.Quaternionfc;
import org.joml.Vector3fc;

/**
 * Created by stuart on 20/01/17.
 */
public interface Transformable {
    Transformable translate(Vector3fc translate);
    Transformable rotate(Quaternionfc rotate);
    Transformable scale(Vector3fc scale);
    Transformable applyTransform();
}
