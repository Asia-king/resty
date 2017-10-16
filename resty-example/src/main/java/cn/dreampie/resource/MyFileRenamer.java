package cn.dreampie.resource;

import cn.dreampie.common.util.stream.FileRenamer;

import java.io.File;

/**
 * @author Dreampie
 */
public class MyFileRenamer extends FileRenamer {
  public File rename(File f) {
    // change dir
    return new File(f.getAbsolutePath().replace("upload", "xx"));
  }
}
