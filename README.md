# E-Ticaret Uygulaması

#### Bu proje, küçük bir e-ticaret sitesi gibi işlev gören bir sistemdir. Kullanıcılar ürün sipariş verebilir, ödeme işlemleri yapılabilir ve sipariş onaylandığında kullanıcıya e-posta ile bildirim gönderilir. Projenin temel amacı, **Iyzico ödeme entegrasyonunu** başarıyla sağlamaktır.

## Genel Senaryo
- Müşteriler sisteme kayıt olur ve giriş yaparlar.
- Mağaza sahipleri ürün ekler, siler ve günceller.
- Ürünler listelenir.
- **SearchService** sayesinde ürün araması ve filtrelemesi yapılır.
- Kullanıcılar **BasketService** aracılığıyla sepete ürün eklerler.
- Sipariş verme işlemi **OrderService** ile gerçekleştirilir.
- Ödeme, **Iyzico** entegrasyonu sayesinde başarıyla tamamlanır.
- Ödeme başarıyla gerçekleştirildikten sonra, kullanıcının mailine sipariş ile alakalı bir bildirim gönderilir ve sepetteki ürünler otomatik olarak silinir.

## Kullanılan Teknolojiler
- **Microservice Mimarisi**: Uygulama, farklı hizmetleri bağımsız olarak yönetmek için mikroservis mimarisi kullanır.
- **Kafka**: Servisler arası mesajlaşma ve asenkron iletişim için Kafka kullanılmıştır.
- **Iyzico Ödeme Entegrasyonu**: Kullanıcıların ödemelerini güvenli bir şekilde yapabilmeleri için Iyzico ödeme sistemi entegre edilmiştir.
- **Spring Boot**: Her bir mikroservis Spring Boot ile geliştirilmiştir.
- **Spring Cloud**: Mikroservislerin keşfi ve yönetimi için Spring Cloud kullanılmıştır.
- **Spring Security**: Güvenlik işlemleri için Spring Security entegrasyonu yapılmıştır.
- **Docker**: Uygulamanın konteynerleştirilmesi ve dağıtımı için Docker kullanılmıştır.
- **Redis**: Uygulama performansını artırmak için önbellekleme amacıyla Redis kullanılmıştır.
- **AOP (Aspect-Oriented Programming)**: Uygulamada kesme noktaları ve çapraz kesim işlemleri için AOP kullanılmıştır.
- **MongoDB**: NoSQL veritabanı olarak MongoDB tercih edilmiştir.
- **PostgreSQL**: İlişkisel veritabanı olarak PostgreSQL kullanılmıştır.
- **Prometheus**: Sistem metriklerini izlemek ve alarmlar oluşturmak için Prometheus kullanılmıştır.
- **Grafana**: Uygulamanın performansını görselleştirmek ve izlemek için Grafana ile entegrasyon sağlanmıştır.

## Servisler
- **BasketService**: Kullanıcıların sepetlerine ürün eklemelerini ve yönetmelerini sağlar.
- **NotificationService**: Sipariş onaylandığında kullanıcılara e-posta gönderimi yapar.
- **CustomerService**: Kullanıcı bilgilerini yönetir.
- **OrderService**: Sipariş işlemlerini yönetir.
- **PaymentService**: Iyzico ödeme sistemini kullanarak ödeme işlemlerini gerçekleştirir.
- **ProductService**: Ürünlerin yönetimi ve listelemesi işlemlerini yapar.
- **DiscoveryService**: Mikroservislerin keşfi ve yönetimi için kullanılır.
- **SearchService**: Ürün arama ve filtreleme işlevselliği sağlar.
- **ConfigServer**: Uygulamanın konfigürasyon ayarlarını tek bir yerden yönetmek için kullanılır.

# API Uç Noktaları

## ProductController

- ```GET /api/v1/product/getAllProducts```  
  Tüm ürünleri getirir.

- ```GET /api/v1/product/getById/product/{id}```  
  Belirtilen ID'ye sahip ürünü getirir.

- ```GET /api/v1/product/products/category/{categoryId}```  
  Belirtilen kategori ID'sine ait tüm ürünleri getirir.

- ```DELETE /api/v1/product/delete/product/{id}```  
  Belirtilen ID'ye sahip ürünü siler.

- ```POST /api/v1/product/create/product```  
  Yeni bir ürün oluşturur.

- ```PUT /api/v1/product/update/product/{id}```  
  Belirtilen ID'ye sahip ürünü günceller.

## CategoryController

- ```GET /api/v1/category/getById/category/{categoryId}```  
  Belirtilen ID'ye sahip kategoriyi getirir.

- ```GET /api/v1/category/listOf/category```  
  Tüm kategorilerin listesini getirir.

- ```DELETE /api/v1/category/delete/category/{categoryId}```  
  Belirtilen ID'ye sahip kategoriyi siler.

- ```POST /api/v1/category/create/category```  
  Yeni bir kategori oluşturur.

- ```PUT /api/v1/category/update/category/{categoryId}```  
  Belirtilen ID'ye sahip kategoriyi günceller.

## Search Service

- ```GET /api/v1/productSearchService/getAll/products```  
  Tüm ürünleri getirir.

- ```GET /api/v1/productSearchService/searchBy/productName```  
  Ürün adını kullanarak ürün araması yapar. `productName` parametresi gereklidir.

- ```GET /api/v1/productSearchService/findBy/priceBetween/{lowPrice}/{highPrice}```  
  Belirtilen fiyat aralığındaki ürünleri bulur. `lowPrice` ve `highPrice` path parametreleri gereklidir.

- ```GET /api/v1/productSearchService/findBy/productPriceAsc```  
  Ürünleri fiyatına göre artan sırayla getirir.

- ```GET /api/v1/productSearchService/findBy/productPriceDesc```  
  Ürünleri fiyatına göre azalan sırayla getirir.

- ```DELETE /api/v1/productSearchService/{id}```  
  Belirtilen ID'ye sahip ürünü siler.

## Payment Service

- ```POST /api/v1/payment/{orderId}```  
  Belirtilen sipariş ID'si için ödeme işlemi gerçekleştirir. `orderId` path parametresi gereklidir ve `PaymentRequest` içeriği gerekmektedir.

## Order Service

- ```GET /api/v1/order/getAll/orders```  
  Tüm siparişleri getirir.

- ```GET /api/v1/order/getById/orders/{orderId}```  
  Belirtilen sipariş ID'sine sahip siparişi getirir. `orderId` path parametresi gereklidir.

- ```GET /api/v1/order/getOrderHistoryForCustomer/{customerId}```  
  Belirtilen müşteri ID'sine sahip müşterinin sipariş geçmişini getirir. `customerId` path parametresi gereklidir.

- ```POST /api/v1/order/create-order/{basketId}```  
  Belirtilen sepet ID'si ile yeni bir sipariş oluşturur. `basketId` path parametresi gereklidir.


## AuthController

- ```POST /api/v1/auth/register```  
  Yeni bir kullanıcı kaydı oluşturur.

- ```POST /api/v1/auth/login```  
  Kullanıcı giriş işlemi yapar ve giriş bilgilerini döner.

## ContactInfoController

- ```GET /api/v1/contactInfo/list/contactInfo```  
  Tüm iletişim bilgilerini getirir.

- ```GET /api/v1/contactInfo/getById/contactInfo/{contactInfoId}```  
  Belirtilen iletişim bilgi ID'sine sahip iletişim bilgilerini getirir. `contactInfoId` path parametresi gereklidir.

- ```GET /api/v1/contactInfo/customer/{customerId}```  
  Belirtilen müşteri ID'sine ait iletişim bilgilerini getirir. `customerId` path parametresi gereklidir.

- ```DELETE /api/v1/contactInfo/delete/contactInfo/{contactInfoId}```  
  Belirtilen iletişim bilgi ID'sine sahip iletişim bilgisini siler. `contactInfoId` path parametresi gereklidir.

- ```POST /api/v1/contactInfo/create/contactInfo```  
  Yeni bir iletişim bilgisi oluşturur.

- ```PUT /api/v1/contactInfo/update/contactInfo/{contactInfoId}```  
  Belirtilen iletişim bilgi ID'sine sahip iletişim bilgisini günceller. `contactInfoId` path parametresi gereklidir.

## RoleController

- ```POST /api/v1/roles/createRoles```  
  Yeni bir rol oluşturur.

- ```POST /api/v1/roles/{userId}/roles/{roleId}```  
  Belirtilen kullanıcıya belirtilen rolü atar. `userId` ve `roleId` path parametreleri gereklidir.

## UserController

- ```GET /api/v1/users/getByIdUser/{id}```  
  Belirtilen kullanıcı ID'sine sahip kullanıcıyı getirir. `id` path parametresi gereklidir.

- ```GET /api/v1/users/getAllUsers```  
  Tüm kullanıcıları getirir.

- ```PUT /api/v1/users/update/users/{userId}```  
  Belirtilen kullanıcıyı günceller. `userId` path parametresi gereklidir.

## BasketController

- ```GET /api/v1/basket/basket/findById/{basketId}```  
  Belirtilen sepet ID'sine sahip sepeti getirir. `basketId` path parametresi gereklidir.

- ```GET /api/v1/basket/product/{productId}```  
  Belirtilen ürün ID'sine göre bir ürün işlemi gerçekleştirir. `productId` path parametresi gereklidir.

- ```GET /api/v1/basket/findBy/customersBasket/{customerId}```  
  Belirtilen müşteri ID'sine ait sepeti getirir. `customerId` path parametresi gereklidir.

- ```POST /api/v1/basket/create```  
  Yeni bir sepet oluşturur. İstemci, müşteri ID'sini ve ürünlerin miktarlarını içeren bir istek gövdesi göndermelidir.

- ```DELETE /api/v1/basket/delete/basket/beforeOrder/{basketId}```  
  Belirtilen sepet ID'sine sahip sepeti siler. `basketId` path parametresi gereklidir.


