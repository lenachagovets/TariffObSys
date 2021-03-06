package ua.nure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.nure.entities.DBImitator;
import ua.nure.entities.EmulateDB;
import ua.nure.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр Доротенко on 06.11.2016.
 */
@Controller
public class HomeController {

    DBImitator db = EmulateDB.init();
    User currentUser = null;
    @RequestMapping(value = "/")
    public ModelAndView index(){
        ModelAndView model = new ModelAndView("index");
        return model;
    }
    @RequestMapping(value = "/mainmenu")
    public ModelAndView mainmenu(){
        ModelAndView model = new ModelAndView("mainmenu");
        model.addObject("user", currentUser);
        return model;
    }

    @RequestMapping(value = "tariffs")
    public ModelAndView tariffs(){
        ModelAndView model = new ModelAndView("tariffs");
        model.addObject("tariffs", db.tariffs);
        return model;
    }
    @RequestMapping(value = "/register")
    public ModelAndView register(){
        if(currentUser != null){
            ModelAndView model = new ModelAndView("index");
            return model;
        }
        ModelAndView model = new ModelAndView("register");
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView signup(@RequestParam("username") String username, @RequestParam("password") String password,
                               @RequestParam("name") String name, @RequestParam("surname") String surname,
                               @RequestParam("mail") String mail, @RequestParam("repassword") String repassword){
        if(currentUser != null){
            ModelAndView model = new ModelAndView("index");
            return model;
        }
        List<String> error = new ArrayList<>();
        //Validation
        if(!Validator.validateUsername(username)){
            error.add("Username must be from 3 to 20 symbols and contains only latin letters and numeral, \"-\" and \"_\" are available too.\n");


        }
        if(!Validator.validateEmail(mail)){
            error.add("Incorrect Email.\n");


        }
        if(password.equals("")){
            error.add("Enter password.\n");

        }
        if(!password.equals(repassword)){
            error.add("Check your password.\n");

        }
        if(!Validator.validateName(name) || !Validator.validateName(surname)){
            error.add("Enter correct Name and Surname.\n");

        }
        for(User u : db.users){
            if(u.getUsername().equals(username)){
                error.add("Username is already exist.\n");

            }
            if(u.getMail().equals(mail)){
                error.add("Email is already exist.\n");

            }
        }
        if(error.size() == 0){
            ModelAndView model = new ModelAndView("index");
            User usr = new User(username, password, name, surname, mail);
            usr.setId(db.users.size() + 1);
            usr.role = db.roles.get(1);
            db.users.add(usr);
            currentUser = usr;
            return model;
        }
        ModelAndView model = new ModelAndView("register");
        model.addObject("error", error);
        model.addObject("username", username);
        model.addObject("name", name);
        model.addObject("surname", surname);
        model.addObject("mail", mail);
        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(){
        currentUser = null;
        ModelAndView model = new ModelAndView("index");
        return model;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView signin(){
        if(currentUser != null){
            ModelAndView model = new ModelAndView("index");
            return model;
        }
        ModelAndView model = new ModelAndView("signin");
        return model;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ModelAndView signinPOST(@RequestParam("username") String username, @RequestParam("password") String password){
        for(User u : db.users){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                currentUser = u;
                ModelAndView model = new ModelAndView("index");
                return model;
            }
        }
        ModelAndView model = new ModelAndView("signin");
        model.addObject("error", "Wrong username or password");
        return model;
    }
    @RequestMapping(value = "/profile")
    public ModelAndView userExplore(){
        if(currentUser == null){
            ModelAndView model = new ModelAndView("index");
            return model;
        }
        ModelAndView model = new ModelAndView("user");
        model.addObject("user", currentUser);
        return model;
    }

    @RequestMapping(value = "/changeUser")
    public ModelAndView changeUser(){
        ModelAndView model = new ModelAndView("changeUser");
        model.addObject("user", currentUser);
        return model;
    }

    @RequestMapping(value = "/changeUser", method = RequestMethod.POST)
    public ModelAndView changeUserPOST(@RequestParam("username") String username, @RequestParam("name") String name,
                                       @RequestParam("surname") String surname){
        if(username.equals("") || name.equals("") || surname.equals("")){
            ModelAndView model = new ModelAndView("changeUser");
            model.addObject("error", "You must enter all parameters");
            model.addObject("user", currentUser);
            return model;
        }
        for(User u : db.users){
            if(u.getUsername().equals(username) && !username.equals(currentUser.getUsername())){
                ModelAndView model = new ModelAndView("changeUser");
                model.addObject("error", "Username is already exist");
                model.addObject("user", currentUser);
                return model;
            }
        }
        currentUser.setUsername(username);
        currentUser.setName(name);
        currentUser.setSurname(surname);
        ModelAndView model = new ModelAndView("user");
        model.addObject("user", currentUser);
        return model;
    }

    @RequestMapping(value = "/changePassword")
    public ModelAndView changePassword(){
        return new ModelAndView("changePassword");
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ModelAndView changePasswordPOST(@RequestParam("oldpassword") String oldpassword, @RequestParam("password") String password,
                                           @RequestParam("repassword") String repassword){
        if(currentUser.getPassword().equals(oldpassword)){
            if(password.equals(repassword)){
                currentUser.setPassword(password);
                ModelAndView model = new ModelAndView("user");
                model.addObject("user", currentUser);
                return model;
            }
            else{
                ModelAndView model = new ModelAndView("changePassword");
                model.addObject("error", "Passwords do not match");
                return model;
            }
        }
        else{
            ModelAndView model = new ModelAndView("changePassword");
            model.addObject("error", "Wrong old password");
            return model;
        }
    }
}
