package org.ashina.mycontact.controller;

import org.ashina.mycontact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import org.ashina.mycontact.entity.Contact;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

//    @GetMapping("/contact")
//    public String list(Model model) {
//        model.addAttribute("contacts", contactService.findAll());
//        return "list";
//    }
//
//    @GetMapping("/contact/search")
//    public String search(@RequestParam("term") String term, Model model) {
//        if (StringUtils.isEmpty(term)) {
//            return "redirect:/contact";
//        }
//
//        model.addAttribute("contacts", contactService.search(term));
//        return "list";
//    }
//
//    @GetMapping("/contact/add")
//    public String add(Model model) {
//        model.addAttribute("contact", new Contact());
//        return "form";
//    }
//
//    @PostMapping("/contact/save")
//    public String save(@Valid Contact contact, BindingResult result, RedirectAttributes redirect) {
//        if (result.hasErrors()) {
//            return "form";
//        }
//        contactService.save(contact);
//        redirect.addFlashAttribute("successMessage", "Saved contact successfully!");
//        return "redirect:/contact";
//    }
//
//    @GetMapping("/contact/{id}/edit")
//    public String edit(@PathVariable("id") Integer id, Model model) {
//        model.addAttribute("contact", contactService.findOne(id));
//        return "form";
//    }
//    @GetMapping("/contact/{id}/delete")
//    public String delete(@PathVariable int id, RedirectAttributes redirect) {
//        contactService.delete(id);
//        redirect.addFlashAttribute("successMessage", "Deleted contact successfully!");
//        return "redirect:/contact";
//    }

    @GetMapping("/contact")
    public ResponseEntity<List<Contact>> getAllContact() {

        List<Contact> listContacts = new ArrayList<>();
        contactService.findAll().forEach(listContacts::add);
        if (listContacts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listContacts, HttpStatus.OK);

    }

    @GetMapping("/contact/search/{name}")
    public ResponseEntity<List<Contact>> search(@PathVariable("name") String name) {
        List<Contact> contacts = contactService.search(name);
        if (contacts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(contacts, HttpStatus.OK);

    }

    @PostMapping(value = "/contact")
    public ResponseEntity add(@RequestBody Contact contact) {
        contactService.save(contact);
        return new ResponseEntity(contact, HttpStatus.CREATED);
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<Contact> get(@PathVariable("id") Integer id) {
        Optional<Contact> tmpContact = contactService.findOne(id);
        if (tmpContact.isPresent()) {
            return new ResponseEntity(tmpContact.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/contact/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody Contact contact) {
        Optional<Contact> tmpContact = contactService.findOne(id);
        if (tmpContact.isPresent()) {
            tmpContact.get().setName(contact.getName());
            tmpContact.get().setEmail(contact.getEmail());
            tmpContact.get().setPhone(contact.getPhone());
            contactService.save(tmpContact.get());
            return new ResponseEntity(tmpContact.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Contact> tmpContact = contactService.findOne(id);
        if (tmpContact.isPresent()) {
            contactService.delete(id);
            return new ResponseEntity(tmpContact, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}