Proje Adı: Otel Yönetim ve Rezervasyon Sistemi
___________________________________________________________________________________
Proje Sahibi: Hakan Cem GERÇEK.
___________________________________________________________________________________
Proje Başlama Tarihi: 18.08.2025
___________________________________________________________________________________
Proje Bitirme Tarihi: 29.08.2025
___________________________________________________________________________________
Yapı: RESTFul Web API.
___________________________________________________________________________________
Dil: Java 24
___________________________________________________________________________________
Teknolojiler: 
  + Spring Framework 6 / Spring Boot 3.5.5, 
  + Maven
___________________________________________________________________________________
Amaç: Bu projede bir otelin rezervasyon ve müşteri yönetiminin ön planda olduğu bir API oluşturmak istedim. Temel controller, service ve repository katmanlarına ek olarak DTO ve DTO'ların Entity'lere dönüşümü için de mapper'lar oluşturdum. Projede gerekli enumları ve basit exception class'larını yazdıktan sonra repository içine kendi istediğim şekilde ekstra sorgu metotları da ekledim. Böylece daha geniş bir kullanıma uygun bir yapı oluşturdum. Projede bir müşteri otelde bulunan mevcut odalardan rezervasyon yapabiliyor. İstediği ödeme yöntemini oluşturduğum Payment entity'sine uygun bir şekilde seçebiliyor. Bütün bu işlemleri ve oda, müşteri, rol, kullanıcı ve rezervasyon oluşturma işlemlerini admin veya resepsiyonist gibi rollerdeki yetkililer yapabiliyor ve aynı zamanda uygun şartlarda görüntüleyebiliyor.
___________________________________________________________________________________
Dependency'ler: 
  + Spring Web
  + Spring Data JPA
  + Lombok
  + Validations
  + Spring DevTools
  + MySQL
  + H2
___________________________________________________________________________________
Proje Paket Şeması:
+ src
+ main
  + java
    + com.otelyonetim.rezervasyon
      + controller
        - CustomerController
        - PaymentController
        - ReservationController
        - RoleController
        - RoomController
        - UserController
      + dto
        + CustomerDTO
          - CustomerCreateDTO
          - CustomerFullResponseDTO
          - CustomerLimitedResponseDTO
          - CustomerUpdateDTO
        + PaymentDTO
          - PaymentCreateDTO
          - PaymentFullResponseDTO
          - PaymentLimitedResponseDTO
          - PaymentUpdateDTO
        + ReservationDTO
          - ReservationCreateDTO
          - ReservationFullResponseDTO
          - ReservationLimitedResponseDTO
          - ReservationUpdateDTO
        + RoleDTO
          - RoleCreateDTO
          - RoleFullResponseDTO
          - RoleLimitedResponseDTO
          - RoleUpdateDTO
        + RoomDTO
          - RoomCreateDTO
          - RoomFullResponseDTO
          - RoomLimitedResponseDTO
          - RoomUpdateDTO
        + UserDTO
          - UserCreateDTO
          - UserFullResponseDTO
          - UserLimitedResponseDTO
          - UserUpdateDTO
      + enums
        - Currency
        - PaymentMethod
        - PaymentStatus
        - ReservationStatus
        - RoomType
      + exception
        - IllegalReservationStateException
        - ResourceNotFoundException
      + Mapper
        - CustomerMapper
        - PaymentMapper
        - ReservationMapper
        - RoleMapper
        - RoomMapper
        - UserMapper
      + repository
        - CustomerRepository
        - PaymentRepository
        - ReservationRepository
        - RoleRepository
        - RoomRepository
        - UserRepository
      + service
        + customer
          - CustomerService
          - CustomerServiceImpl
        + payment
          - PaymentService
          - PaymentServiceImpl
        + reservation
          - ReservationService
          - ReservationServiceImpl
        + role
          - RoleService
          - RoleServiceImpl
        + room
          - RoomService
          - RoomServiceImpl
        + user
          - UserService
          - UserServiceImpl
      - AppRun.java
______________________________________________________________________________________________________________________________________________________________________
Database konfigürasyonu :
  - JDBC bağlantısı: spring.datasource.url=jdbc:mysql://localhost:3306/otel_rezervasyon?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
  - JPA / Hibernate Ayarları: spring.jpa.hibernate.ddl-auto=update
______________________________________________________________________________________________________________________________________________________________________

+ Test için gerekli end pointler ve JSON verileri:

POST --> .../api/customers

```json
{
    "firstName" : "Ahmet",
    "lastName" : "Demir",
    "email" : "ahmet@ornek.com",
    "phone" : "5553332211",
    "tckn" : "11111111111"
}
```

POST --> .../api/rooms

```json
{
    "roomNumber" : "101",
    "roomType" : "SUITE",
    "capacity" : "1",
    "pricePerNight" : "250",
    "description" : "oda 1"
}
```

POST --> .../api/reservations

```json
{
  "customerId": 1,
  "roomId": 1,
  "checkInDate": "2025-09-01T14:00:00",
  "checkOutDate": "2025-09-05T12:00:00",
  "adultCount": 1,
  "childCount": 0,
  "totalPrice": 150,
  "note": "Tek kişi."
}
```

POST --> .../api/payments/reservation/1

```json
{
  "amount": 250,
  "currency": "TRY",
  "paymentMethod": "CREDIT_CARD",
  "cardHolderName": "Ahmet Demir",
  "cardLastDigits": "1234",
  "cardBrand": "VISA",
  "cardExpiryDate": "2026-12-31T23:59:59",
  "note": "Rezervasyon ödemesi tamamlandı."
}
```

POST --> .../api/users
```json
{
  "username": "Admin",
  "password": "123456",
  "email": "admin@ornek.com",
  "isActive": "true"
}
```

POST --> .../api/roles

```json
{
    "name": "Admin"
}
```

______________________________________________________________________________________________________________________________________________________________________
iletişim: hakancg05@gmail.com
