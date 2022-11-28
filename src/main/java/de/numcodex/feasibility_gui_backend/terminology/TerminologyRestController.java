package de.numcodex.feasibility_gui_backend.terminology;


import de.numcodex.feasibility_gui_backend.terminology.api.CategoryEntry;
import de.numcodex.feasibility_gui_backend.terminology.api.TerminologyEntry;
import de.numcodex.feasibility_gui_backend.terminology.references.ReferenceCandidateRequest;
import de.numcodex.feasibility_gui_backend.terminology.references.ReferenceCandidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
 Rest interface to get the terminology definitions from the UI backend which itself request the
 terminology information from the ui terminology service
 */


@RequestMapping({"api/v1/terminology", "api/v2/terminology"})
@RestController
@CrossOrigin
public class TerminologyRestController {

    private final TerminologyService terminologyService;

    @Autowired
    public TerminologyRestController(TerminologyService terminologyService) {
        this.terminologyService = terminologyService;
    }

    @GetMapping("entries/{nodeId}")
    @PreAuthorize("hasRole(@environment.getProperty('app.keycloakAllowedRole'))")
    public TerminologyEntry getEntry(@PathVariable UUID nodeId) {
        return terminologyService.getEntry(nodeId);
    }

    @GetMapping("root-entries")
    @PreAuthorize("hasRole(@environment.getProperty('app.keycloakAllowedRole'))")
    public List<CategoryEntry> getCategories() {
        return terminologyService.getCategories();
    }

    @GetMapping("selectable-entries")
    @PreAuthorize("hasRole(@environment.getProperty('app.keycloakAllowedRole'))")
    public List<TerminologyEntry> getSelectableEntries(@RequestParam("query") String query,
                                                       @RequestParam(value = "categoryId", required = false) UUID categoryId) {
        return terminologyService.getSelectableEntries(query, categoryId);
    }

    @GetMapping(value = "ui_profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole(@environment.getProperty('app.keycloakAllowedRole'))")
    public String getUiProfile(@RequestParam("system") String system, @RequestParam("code") String code, @RequestParam(value = "version", required = false) String version) {
        return terminologyService.getUiProfile(system, code, version);
    }

    @PostMapping(value = "reference-options", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole(@environment.getProperty('app.keycloakAllowedRole'))")
    public ReferenceCandidateResponse getReferenceOptions(@RequestBody ReferenceCandidateRequest referenceCandidateRequest) {
        return terminologyService.checkReferenceCandidates(referenceCandidateRequest);
    }
}
