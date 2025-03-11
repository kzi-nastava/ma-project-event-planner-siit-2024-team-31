package com.example.ep2024.data.repository;

import com.example.ep2024.domain.repository.ServiceRepository;
import com.example.ep2024.domain.model.Service;
import com.example.ep2024.domain.model.service.ConfirmationMethod;

import java.util.ArrayList;

public class ServiceRepositoryImpl implements ServiceRepository {
    @Override
    public Service getService(String id) {
        return new Service(
                id,                          // String id
                "Sample Service",            // String name
                "Sample Description",        // String description
                "Sample Category",           // String category
                0.0,                         // double value1
                0.0,                         // double value2
                new ArrayList<>(),           // List<String> list1
                new ArrayList<>(),           // List<String> list2
                "Sample Location",           // String location
                false,                       // boolean flag1
                false,                       // boolean flag2
                null,                        // Integer number1 (или можно указать значение, например, 0, если не допускается null)
                null,                        // Integer number2
                null,                        // Integer number3
                0,                           // int value3
                0,                           // int value4
                ConfirmationMethod.AUTOMATIC,    // ConfirmationMethod confirmationMethod (убедитесь, что у ConfirmationMethod есть конструктор по умолчанию)\n
                false                        // boolean flag3
        );
    }
}