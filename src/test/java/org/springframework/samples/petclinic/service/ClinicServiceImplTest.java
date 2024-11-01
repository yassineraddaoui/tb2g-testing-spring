package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    public static final List<PetType> PET_TYPES = List.of(new PetType());
    public static final Owner OWNER = new Owner();
    public static final Visit VISIT = new Visit();
    public static final Pet PET = new Pet();

    public static final List<Owner> OWNERS_LIST = List.of(OWNER);
    public static final List<Visit> VISIT_LIST = List.of(VISIT);

    @Mock
    PetRepository petRepository;
    @Mock
    VetRepository vetRepository;
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    ClinicServiceImpl clinicServiceImpl;

    @Test
    void findPetTypes() {
        given(petRepository.findPetTypes()).willReturn(PET_TYPES);
        var result = clinicServiceImpl.findPetTypes();
        then(petRepository).should().findPetTypes();
        assertThat(result).isEqualTo(PET_TYPES);
    }

    @Test
    void findOwnerById() {
        given(ownerRepository.findById(anyInt()))
                .willReturn(OWNER);
        var result = clinicServiceImpl.findOwnerById(1);
        then(ownerRepository).should().findById(anyInt());
        assertThat(result).isEqualTo(OWNER);

    }

    @Test
    void findOwnerByLastName() {

        given(ownerRepository.findByLastName(anyString()))
                .willReturn(OWNERS_LIST);
        var result = clinicServiceImpl.findOwnerByLastName("yassine");
        then(ownerRepository).should().findByLastName(anyString());
        assertThat(result).isEqualTo(OWNERS_LIST);

    }

    @Test
    void saveOwner() {
        clinicServiceImpl.saveOwner(OWNER);
        then(ownerRepository).should().save(OWNER);
    }

    @Test
    void saveVisit() {
        clinicServiceImpl.saveVisit(VISIT);
        then(visitRepository).should().save(VISIT);
    }

    @Test
    void findPetById() {
        given(petRepository.findById(anyInt()))
                .willReturn(PET);
        var result = clinicServiceImpl.findPetById(1);
        then(petRepository).should().findById(anyInt());
        assertThat(result).isEqualTo(PET);

    }

    @Test
    void savePet() {
        clinicServiceImpl.savePet(PET);
        then(petRepository).should().save(PET);

    }

    @Test
    void findVets() {
        var vets = List.of(new Vet());
        given(vetRepository.findAll()).willReturn(vets);
        var result = clinicServiceImpl.findVets();
        then(vetRepository).should().findAll();
        assertThat(result).isEqualTo(vets);
    }

    @Test
    void findVisitsByPetId() {
        given(visitRepository.findByPetId(anyInt()))
                .willReturn(VISIT_LIST);
        var result = clinicServiceImpl.findVisitsByPetId(1);

        then(visitRepository).should().findByPetId(anyInt());
        assertThat(result).isEqualTo(VISIT_LIST);
    }
}