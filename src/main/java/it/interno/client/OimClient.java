package it.interno.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "oim", path = "/oim")
public interface OimClient {

    @PutMapping("/ruolo/rename")
    void renameRuolo(@RequestParam String nomeRuoloOld, @RequestParam String nomeRuoloNew);

    @PostMapping("/ruolo-utente/associazione")
    void associazioneRuolo(@RequestParam String codiceUtente, @RequestParam String nomeRuolo);

    @PostMapping("/ruolo-utente/disassociazione")
    void rimozioneRuolo(@RequestParam String codiceUtente, @RequestParam String nomeRuolo);



    @PostMapping("/ruolo/delete/massivo")
    void deleteRuoli(@RequestBody List<String> nomiRuoli);

    @GetMapping("/ruolo")
    public String ricercaRuoloId(@RequestParam String nomeRuolo);

    @PostMapping("/ruolo-utente/disassociazione/utenti")
    void rimozioneRuoloAUtenti(@RequestParam String nomeRuolo, @RequestBody List<String> codiciUtenti);
}
