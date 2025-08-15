package org.esfe.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/barbero")
public class BarberoController {

        @GetMapping
        public String index() {
            return "barbero/index";
        }


}
