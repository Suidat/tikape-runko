package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;
import tikape.runko.domain.Aihe;
import tikape.runko.domain.Viesti;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database2  = new Database("jdbc:sqlite:opiskelijat.db");
        Database database = new Database("jdbc:sqlite:keskustelupalsta.db");
        database.init();

        ViestiDao viestiDao = new ViestiDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        AiheDao aiheDao = new AiheDao(database );

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database2);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("aihelista", aiheDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());



        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());

        get("/:id", (req, res) -> {
            HashMap map = new HashMap<>();
                    map.put("title", aiheDao.findOne(Integer.parseInt(req.params("id"))));
                    map.put("keskustelulista", keskusteluDao.findAll());

            return new ModelAndView(map,"Aihe");
                }, new ThymeleafTemplateEngine());

        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
        }, new ThymeleafTemplateEngine());
    }
}
