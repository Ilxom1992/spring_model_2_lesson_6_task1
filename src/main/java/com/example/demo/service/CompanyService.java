package com.example.demo.service;

import com.example.demo.config.GetTheUser;
import com.example.demo.entity.Address;
import com.example.demo.entity.Company;
import com.example.demo.entity.User;
import com.example.demo.payload.CompanyDto;
import com.example.demo.payload.Response;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {
    final CompanyRepository companyRepository;
    final AddressRepository addressRepository;
    final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, AddressRepository addressRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    //  CREATE
    public Response add(CompanyDto companyDto){
        Company company=new Company();
        company.setName(companyDto.getName());
        if (companyDto.getCompanyId()!=null){
            Optional<Company> optionalCompany = companyRepository.findById(companyDto.getCompanyId());
            if (!optionalCompany.isPresent()){
                return new Response("Company not found",false);
            }
            Company company1 = optionalCompany.get();
            company.setCompany(company1);
            Address address=new Address();
            address.setStreet(companyDto.getStreet());
            address.setHouseNumber(companyDto.getHouseNumber());
            address.setDistrict(companyDto.getDistrict());
            address.setRegion(companyDto.getRegion());
            Address address1 = addressRepository.save(address);
            company.setAddress(address1);
            return new Response("object saved",true,companyRepository.save(company));

        }
        Address address=new Address();
        address.setStreet(companyDto.getStreet());
        address.setHouseNumber(companyDto.getHouseNumber());
        address.setDistrict(companyDto.getDistrict());
        address.setRegion(companyDto.getRegion());
        Address address1 = addressRepository.save(address);
        company.setAddress(address1);
        GetTheUser getTheUser=new GetTheUser();
        User userDirector = userRepository.findById(getTheUser.getCurrentAuditor().get()).get();
        //QO'SHILGAN COMPANIYANI USERGA DIRECTORGA BIRICTIRDIC
        Company save = companyRepository.save(company);

        userDirector.setCompany(company);

        userRepository.save(userDirector);

        return new Response("object saved",true,save);
    }
    //READ
    public Response get(){
        return new Response("object ",true,companyRepository.findAll());
    }
    //UPDATE
    public Response edit(Integer id,CompanyDto companyDto){
        Optional<Company> optionalCompany1 = companyRepository.findById(id);
        if (!optionalCompany1.isPresent()){
            return new Response("Company Vot found",false);
        }
        Company company=optionalCompany1.get();
        company.setName(companyDto.getName());
        if (companyDto.getCompanyId()!=null){
            Optional<Company> optionalCompany = companyRepository.findById(companyDto.getCompanyId());
            if (!optionalCompany.isPresent()){
                return new Response("Company Vot found",false);
            }
            Company company1 = optionalCompany.get();
            company.setCompany(company1);
        }
        return new Response("object edited",true);
    }
    //DELETE
    public Response delete( Integer id ){
        companyRepository.deleteById(id);
        return new Response("object deleted",true);
    }
    //READ BY ID
    public Response getById( Integer id ){

        return new Response("object deleted",true,companyRepository.findById(id));
    }
}
