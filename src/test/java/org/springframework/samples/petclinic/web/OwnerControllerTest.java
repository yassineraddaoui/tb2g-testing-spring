package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    @Autowired
    OwnerController ownerController;
    @Autowired
    ClinicService clinicService;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void setAllowedFields() {

    }

    @Test
    void testProcessCreationForm() throws Exception {

        mockMvc.perform(post("/owners/new")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("address", "Street")
                        .param("city", "City")
                        .param("telephone", "3151231234"))
                .andExpect(status().is3xxRedirection())

        ;
    }

    @Test
    void testProcessCreationFormNotValid() throws Exception {

        mockMvc.perform(post("/owners/new")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("city", "City"))
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processUpdateOwnerFormHasError() throws Exception {
        mockMvc
                .perform(post("/owners/{ownerId}/edit", 1)
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("address", "Address"))
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "city"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processUpdateOwnerForm() throws Exception {

        mockMvc
                .perform(post("/owners/{ownerId}/edit", 1)
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("city", "City")
                        .param("telephone", "3151231234")
                        .param("address", "Address"))
                .andExpect(status().is3xxRedirection());
        verify(clinicService).saveOwner(any(Owner.class));
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void testFindByNameNotFound() throws Exception {
        mockMvc.perform(get("/owners")
                        .param("lastname", "Dont find me"))
                .andExpect(status().isOk());
        verify(clinicService).findOwnerByLastName("Dont find me");

    }

    @Test
    void testFindByNameNull() throws Exception {
        var owner = new Owner();
        owner.setId(5);
        when(clinicService.findOwnerByLastName(anyString()))
                .thenReturn(List.of(owner));
        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + owner.getId()));
        verify(clinicService).findOwnerByLastName("");

    }

    @Test
    void testFindByNameMultiple() throws Exception {
        when(clinicService.findOwnerByLastName(anyString()))
                .thenReturn(List.of(new Owner(), new Owner()));
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));
        verify(clinicService).findOwnerByLastName(anyString());
    }

    @Test
    void processCreationForm() {
    }

    @Test
    void initFindForm() {
    }

    @Test
    void processFindForm() {
    }

    @Test
    void initUpdateOwnerForm() {
    }


    @Test
    void showOwner() {
    }
}