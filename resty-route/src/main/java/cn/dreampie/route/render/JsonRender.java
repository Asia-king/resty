package cn.dreampie.route.render;

import cn.dreampie.common.Render;
import cn.dreampie.common.http.ContentType;
import cn.dreampie.common.http.HttpRequest;
import cn.dreampie.common.http.HttpResponse;
import cn.dreampie.common.util.json.Jsoner;

/**
 * Created by ice on 14-12-29.
 *
 */
public class JsonRender extends Render {

  public void render(HttpRequest request, HttpResponse response, Object out) {
    if (out != null) {
      response.setContentType(ContentType.JSON);
      if (out instanceof String) {
        if (((String) out).startsWith("\"") || ((String) out).startsWith("{") || ((String) out).startsWith("[")) {
          write(request, response, (String) out);
        } else {
          write(request, response, "\"" + out + "\"");
        }
      } else {
        String json = Jsoner.toJSON(out);
        write(request, response, json);
      }
    }
  }
}