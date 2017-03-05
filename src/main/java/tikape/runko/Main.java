package tikape.runko;

import java.util.HashMap;

import spark.ModelAndView;

import static spark.Spark.*;

import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;
import tikape.runko.domain.Aihe;
import tikape.runko.domain.Viesti;
import tikape.runko.domain.Keskustelu;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        Database database = new Database("jdbc:sqlite:keskustelupalsta.db");
        database.init();

        ViestiDao viestiDao = new ViestiDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        AiheDao aiheDao = new AiheDao(database);

        //Aiheiden lista.
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihelista", aiheDao.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        //Keskusteluiden lista.
        get("/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            int id = Integer.parseInt(req.params("id"));

            map.put("title", aiheDao.findOne(id));
            map.put("keskustelulista", keskusteluDao.findAllInAihe(id));
            map.put("määrä", keskusteluDao.findAll());
            return new ModelAndView(map, "Aihe");
        }, new ThymeleafTemplateEngine());

        //Keskustelun sisältö.
        get("/keskustelut/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            int id = Integer.parseInt(req.params("id"));
            map.put("title", keskusteluDao.findOne(id));
            map.put("viestilista", viestiDao.findAllInKeskustelu(id));
            return new ModelAndView(map, "Keskustelu");
        }, new ThymeleafTemplateEngine());


        //Uuden aiheen lisääminen.
        post("/", (req, res) -> {
            if(req.queryParams("aihe").equals("")){
                res.redirect("/");
                return "ok";
            }
            aiheDao.add(new Aihe(req.queryParams("aihe")));
            res.redirect("/");
            return "ok";
        });

        //Uuden keskustelun lisääminen.
        post("/:id", (req, res) -> {
            if(req.queryParams("keskustelu").equals("")){
                res.redirect("/" + req.params(":id"));
                return "ok";
            }
            keskusteluDao.add(new Keskustelu(Integer.parseInt(req.params(":id")), req.queryParams("keskustelu")));
            res.redirect("/" + req.params(":id"));
            return "ok";
        });

        //Uuden viestin lisääminen.
        post("/keskustelut/:id", (Request req, Response res) -> {
            int id = Integer.parseInt(req.params(":id"));

            if(req.queryParams("lähettäjä").equals("")||(req.queryParams("viesti").equals(""))){
                res.redirect("/keskustelut/" + id);
                return "ok";
            }

            viestiDao.add(new Viesti(req.queryParams("lähettäjä"),
                    req.queryParams("viesti"), id));
            res.redirect("/keskustelut/" + id);
            return "ok";
        });

        //Aiheen poistaminen.
        get("/poista/aihe/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "login");
        }, new ThymeleafTemplateEngine());

        post("/poista/aihe/:id", (req,res) ->{
            Aihe a = aiheDao.findOne(Integer.parseInt(req.params(":id")));
            if(req.queryParams("username").equals("admin")&&
                    req.queryParams("password").equals("Tämä salasana on helppo murtaa")&&a!=null)
            aiheDao.delete(Integer.parseInt(req.params(":id")));
            res.redirect("/");
            return "ok";
        });

        //Keskustelun poistaminen,
        get("/poista/keskustelu/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "login");
        }, new ThymeleafTemplateEngine());

        post("/poista/keskustelu/:id", (req,res) ->{
            Keskustelu k = keskusteluDao.findOne(Integer.parseInt(req.params(":id")));

            if(req.queryParams("username").equals("admin")&&
                    req.queryParams("password").equals("Tämä salasana on helppo murtaa")&&k!=null)
                keskusteluDao.delete(Integer.parseInt(req.params(":id")));
            if(k!=null)
            res.redirect("/" + k.getAihe());
            else res.redirect("/");
            return "ok";
        });

        //Viestin poistaminen.
        get("/poista/viesti/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "login");
        }, new ThymeleafTemplateEngine());

        post("/poista/viesti/:id", (req,res) ->{
            Viesti v = viestiDao.findOne(Integer.parseInt(req.params(":id")));
            if(req.queryParams("username").equals("admin")&&
                    req.queryParams("password").equals("Tämä salasana on helppo murtaa")&&v!=null)
                viestiDao.delete(Integer.parseInt(req.params(":id")));
            if(v!=null)
            res.redirect("/keskustelut/"+v.getKeskusteluId());
            else res.redirect("/");
            return "ok";
        });
    }
}
