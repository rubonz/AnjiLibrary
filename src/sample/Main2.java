package sample;

import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.anji.document.tex.TeXProjectManager;
import com.darkempire.anji.document.tex.util.TeXUtil;
import com.darkempire.anji.log.Log;

import java.io.File;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) throws Exception {
        TeXProjectManager manager = new TeXProjectManager(new File("./"));
        TeXEventWriter out = manager.getMainFileManager().getEventWriter();
        TeXUtil.initSimpleHeader(out);
        out.openEnvironment("document");
        out.text("asdasdassdgsagasdf");
        Log.setAllow(TeXProjectManager.texBuild, true);
        manager.build();
    }
}
