package io.lx.common.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * DESC: Created by Sam on 2020/9/22 14:19.
 */
@Slf4j
@Component
public class OkHttpUtil {

  private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

  private static OkHttpClient okHttpClient;

  @Autowired
  public OkHttpUtil(OkHttpClient okHttpClient) {
    OkHttpUtil.okHttpClient = okHttpClient;
  }

  /**
   * get
   *
   * @param url     请求的url
   * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
   * @return
   */
  public String get(String url, Map<String, String> queries) {
    StringBuilder sb = new StringBuilder(url);
    if (queries != null && queries.keySet().size() > 0) {
      boolean firstFlag = true;
      for (Map.Entry<String, String> entry : queries.entrySet()) {
        if (firstFlag) {
          sb.append("?").append(entry.getKey()).append("=").append(entry.getValue());
          firstFlag = false;
        } else {
          sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
      }
    }
    Request request = new Request.Builder()
        .url(sb.toString())
        .build();
    return send(request);
  }

  /**
   *
   * @param url 请求地址
   * @param requestBody 请求体
   * @return json
   */

  public String post(String url, String requestBody) {
    Request request = new Request.Builder().url(url).header("Content-Type", "application/json").post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody)).build();
    return send(request);
  }

  private String send(Request request) {
    try (Response response = okHttpClient.newCall(request).execute()) {
      int status = response.code();
      if (response.isSuccessful()) {
        return response.body() != null ? response.body().string() : "";
      }
    } catch (Exception e) {
      logger.error("okhttp3 post error >> ex = {}", ExceptionUtils.getStackTrace(e));
    }
    return "";
  }

  public String postOcrFile(String url, MultipartFile file) throws IOException {
    String fileName = cn.hutool.core.io.FileUtil.mainName(file.getOriginalFilename()) + "_"
            + System.currentTimeMillis() + "."
            + FileUtil.fileExtensionName(file.getOriginalFilename());
    MediaType JSON = MediaType.parse("image/jpeg");
    RequestBody fileBody = RequestBody.create(JSON, file.getBytes());
    RequestBody requestBody = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", fileName, fileBody)
            .build();
    Request request = new Request.Builder()
            .url(url)
//                .header("Content-Type","multipart/form-data")
            .post(requestBody)
            .build();
    return send(request);
  }
}
