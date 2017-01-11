package uk.co.reillyfamily.game.modelparser;

import uk.co.reillyfamily.game.unloaded.UnloadedModel;

import java.io.File;
import java.io.InputStream;

/**
 * Created by stuart on 11/01/17.
 */
public interface ModelParser {
    UnloadedModel parse(InputStream in);
}
