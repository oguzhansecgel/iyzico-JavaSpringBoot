# E-Ticaret Uygulaması

#### Bu proje, küçük bir e-ticaret sitesi gibi işlev gören bir sistemdir. Kullanıcılar ürün sipariş verebilir, ödeme işlemleri yapılabilir ve sipariş onaylandığında kullanıcıya e-posta ile bildirim gönderilir. Projenin temel amacı, **Iyzico ödeme entegrasyonunu** başarıyla sağlamaktır.

## Kullanılan Teknolojiler
- **Microservice Mimarisi**: Uygulama, farklı hizmetleri bağımsız olarak yönetmek için mikroservis mimarisi kullanır.
- **Kafka**: Servisler arası mesajlaşma ve asenkron iletişim için Kafka kullanılmıştır.
- **Iyzico Ödeme Entegrasyonu**: Kullanıcıların ödemelerini güvenli bir şekilde yapabilmeleri için Iyzico ödeme sistemi entegre edilmiştir.
- **Spring Boot**: Her bir mikroservis Spring Boot ile geliştirilmiştir.
- **Spring Cloud**: Mikroservislerin keşfi ve yönetimi için Spring Cloud kullanılmıştır.
- **Spring Security**: Güvenlik işlemleri için Spring Security entegrasyonu yapılmıştır.

## Servisler
- **BasketService**: Kullanıcıların sepetlerine ürün eklemelerini ve yönetmelerini sağlar.
- **NotificationService**: Sipariş onaylandığında kullanıcılara e-posta gönderimi yapar.
- **CustomerService**: Kullanıcı bilgilerini yönetir.
- **OrderService**: Sipariş işlemlerini yönetir.
- **PaymentService**: Iyzico ödeme sistemini kullanarak ödeme işlemlerini gerçekleştirir.
- **ProductService**: Ürünlerin yönetimi ve listelemesi işlemlerini yapar.
- **DiscoveryService**: Mikroservislerin keşfi ve yönetimi için kullanılır.

## Proje Kurulumu

Projeyi klonlayın:

```bash
git clone https://github.com/oguzhansecgel/iyzico-JavaSpringBoot.git
