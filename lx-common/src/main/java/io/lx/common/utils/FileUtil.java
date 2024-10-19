package io.lx.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 项目名称：WeiJianUtils 类名称：FileUtil 类描述：文件操作工具类 创建人：WeiJian 创建时间：2014年8月22日 上午11:11:09 备注：
 *
 * @version 1.0
 */
public class FileUtil {

  protected static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
  public static final int BUFFER_SIZE = 16 * 1024;

  /**
   * 判断是否为图片类型
   *
   * @param extName 扩展名
   * @return
   */
  public static boolean isImage(String extName) {
    return "jpg".equalsIgnoreCase(extName)
        || "jpeg".equalsIgnoreCase(extName)
        || "png".equalsIgnoreCase(extName)
        || "tif".equalsIgnoreCase(extName)
        || "bmp".equalsIgnoreCase(extName);
  }

  public static boolean isNotImage(String extName) {
    return !isImage(extName);
  }


  /**
   * 获取后缀名
   *
   * @param fileName * @return
   */
  public static String fileExtensionName(String fileName) {
    if (fileName == null) {
      return null;
    }
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

  /**
   * 获取不带后缀名的文件名
   *
   * @param file
   * @return
   */
  public static String fileName(File file) {
    if (fileExist(file.getAbsolutePath())) {
      return file.getName().substring(0, file.getName().lastIndexOf("."));
    } else {
      return null;
    }
  }

  /**
   * 获取不带后缀名的文件名
   *
   * @param file
   * @return
   */
  public static String fileName(String file) {
    return file.substring(0, file.lastIndexOf("."));
  }

  /**
   * 判断文件是否存在，存在返回true
   *
   * @param pathAndFile
   * @return
   */
  public static boolean fileExist(String pathAndFile) {
    File file = new File(pathAndFile);
    if (file.exists()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 拷贝文件
   *
   * @param source
   * @param dst
   */
  public static void copy(InputStream source, File dst) {
    InputStream in = source;
    OutputStream out = null;
    try {
      out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
      byte[] buffer = new byte[BUFFER_SIZE];
      int len = -1;
      while ((len = in.read(buffer)) > 0) {
        out.write(buffer, 0, len);
      }
    } catch (Exception e) {
      logger.error(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
    } finally {
      try {
        if (null != in) {
          in.close();
        }
      } catch (Exception e) {
        logger.error(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
      }
      try {
        if (null != out) {
          out.close();
        }
      } catch (Exception e) {
        logger.error(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
      }
    }
  }

  /**
   * 创建文件及其父目录。
   *
   * @param file
   * @throws Exception
   */
  public static File createFile(File file) throws Exception {
    createParentFolder(file);
    if (!file.createNewFile()) {
      throw new Exception("create file failure!");
    }
    return file;
  }

  /**
   * 创建父目录
   *
   * @param file
   * @throws Exception
   */
  private static void createParentFolder(File file) throws Exception {
    if (!file.getParentFile().exists()) {
      if (!file.getParentFile().mkdirs()) {
        throw new Exception("create parent directory failure!");
      }
    }
  }


  public static byte[] file2byte(File file) {
    byte[] buffer = null;
    try {
      FileInputStream fis = new FileInputStream(file);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] b = new byte[1024];
      int n;
      while ((n = fis.read(b)) != -1) {
        bos.write(b, 0, n);
      }
      fis.close();
      bos.close();
      buffer = bos.toByteArray();
    } catch (FileNotFoundException e) {
      logger.error(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
    } catch (IOException e) {
      logger.error(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
    }
    return buffer;
  }

  /**
   * 将内容写到文件（默认UTF-8）
   *
   * @param filePath
   * @param content
   */
  public static void writeToFile(String filePath, String content) {
    writeToFile(filePath, content, "UTF-8");
  }

  /**
   * 将内容写到文件
   *
   * @param filePath
   * @param content
   * @param encoding
   */
  public static void writeToFile(String filePath, String content, String encoding) {
    try {
      File f = new File(filePath);
      if (!f.exists()) {
        createFile(f);
      }

      OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), encoding);
      BufferedWriter writer = new BufferedWriter(write);
      writer.write(content);
      writer.flush();
      writer.close();
    } catch (Exception e) {
      logger.error(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
    }
  }
  public static void byte2File(byte[] buf, String filePath, String fileName) throws IOException {
    BufferedOutputStream bos = null;
    FileOutputStream fos = null;
    File file = null;
    try {
      File dir = new File(filePath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      file = new File(filePath + File.separator + fileName);
      fos = new FileOutputStream(file);
      bos = new BufferedOutputStream(fos);
      bos.write(buf);
    } finally {
      if (bos != null) {
        try {
          bos.close();
        } catch (IOException e) {
          logger.error(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
          logger.error(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
        }
      }
    }
  }
}
