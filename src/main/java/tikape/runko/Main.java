package tikape.runko;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import spark.ModelAndView;

import static spark.Spark.*;

import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;
import tikape.runko.domain.Aihe;
import tikape.runko.domain.Viesti;
import tikape.runko.domain.Keskustelu;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelupalsta.db");
        database.init();

        DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
        Date date = new Date();
        ViestiDao viestiDao = new ViestiDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        AiheDao aiheDao = new AiheDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihelista", aiheDao.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            int id = Integer.parseInt(req.params("id"));
            map.put("title", aiheDao.findOne(id));
            map.put("keskustelulista", keskusteluDao.findAllInAihe(id));
            map.put("määrä", viestiDao.viestienMaara(id).toString());
            return new ModelAndView(map, "Aihe");
        }, new ThymeleafTemplateEngine());

        get("/keskustelut/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            int id = Integer.parseInt(req.params("id"));
            map.put("title", keskusteluDao.findOne(id));
            map.put("viestilista", viestiDao.findAllInKeskustelu(id));
            return new ModelAndView(map, "Keskustelu");
        }, new ThymeleafTemplateEngine());

        post("/uusi/aihe/", (req, res) -> {
            aiheDao.add(new Aihe(req.queryParams("nimi")));
            res.redirect("/ ");
            return "ok";
        });

        post("/uusi/keskustelu/:id", (req, res) -> {
            keskusteluDao.add(new Keskustelu(Integer.parseInt(req.params(":id")), req.queryParams("nimi")));
            res.redirect("/"+req.params(":id"));
            return "ok";
        });

        post("/uusi/viesti/:id", (req, res) ->{
            viestiDao.add(new Viesti());
            res.redirect("/keskustelut/"+req.params(":id"));
            return "ok";
        });

        get("/poista/aihe/:id", (req, res) -> {
            aiheDao.delete(Integer.parseInt(req.params(":id")));
            res.redirect("/");
            return "ok";
        });
    }
}
