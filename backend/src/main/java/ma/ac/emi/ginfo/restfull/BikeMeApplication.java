package ma.ac.emi.ginfo.restfull;

import ma.ac.emi.ginfo.restfull.entities.*;
import ma.ac.emi.ginfo.restfull.security.SecurityConfig;
import ma.ac.emi.ginfo.restfull.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Arrays;
import java.util.List;


//@CrossOrigin(origins = "http://localhost:4200")
@SpringBootApplication
@EnableCaching
@EnableAsync
@Import(SecurityConfig.class)
public class BikeMeApplication {
    @Autowired
    RentalService rentalService ;

    @Autowired
    UserService userService ;

    @Autowired
    BikeService bikeService ;
    @Autowired
    LocationService locaService ;

    @Autowired
    PicturesStorageService picturesStorageService;

    @Autowired
    MailService mailService ;

    public static void main(String[] args) {
        SpringApplication.run(BikeMeApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ac) {
        return args -> {

            this.picturesStorageService.init();
            /// create users
            userService.addUser(new User("El Afife","Houda","elafi@gmail.com","password",Role.USER));
            userService.addUser(new User("El Main","Samya","samya@gmail.com","password",Role.ADMIN));
            userService.addUser(new User("Aneddame","Mouna","mouna@gmail.com","password",Role.ADMIN));
            userService.addUser(new User("Arbani","Mohamed","arbani@gmail.com","password",Role.USER));
            //create Locations

            Location agdal = new Location("Agdal","Rabat","Maroc",-6.855586, 34.002135,"10090");
            Location hayRyad = new Location("Hay Ryad","Rabat","Maroc",-6.87263, 33.955946,"10110");
            Location babAlHad = new Location("Bab Al Had","Rabat","Maroc",-6.840963, 34.021671,"10100");

            Location maarif = new Location("Maarif","Casablanca","Maroc",-7.627689, 33.567033,"20000");
            Location ainDiab = new Location("AIN DIAB","Casablanca","Maroc",-7.673802, 33.589219,"20090");
            Location ainChock = new Location("Aïn Chock","Casablanca","Maroc",-7.583149, 33.533859,"20130");

            Location hayCharaf = new Location("Hay Charaf","Marrakech","Maroc",-8.02061, 31.673734,"40000");
            Location hayAlMassar = new Location("Hay Al Massar","Marrakech","Maroc",-8.036972, 31.675535,"40160");
            Location elAzzouzia = new Location("El Azzouzia","Marrakech","Maroc",-8.071072, 31.688954,"40040");

            Arrays.asList(agdal,hayRyad,babAlHad,maarif,ainDiab,ainChock,hayCharaf,hayAlMassar,elAzzouzia).forEach(location -> locaService.createLoca(location));


            User owner1 = new User("Samya","El main","samya01@gmail.com","123456");
            User owner2 = new User("Sabrine","Alain","alain@gmail.com","alain0123");

            Bike bike = new Bike("Thunderbolt","Urban","M3L","2019","The \"Thunderbolt\" - a sleek and powerful sport bike with a top speed of 180 mph and a 0-60 time of 2.5 seconds.",Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/20303/thumb_1619547401-1548500645-Giant_triple_x_Wit.jpg")), agdal,20,3,false,owner1,null,null,true,0.1,0.2,2);
            Bike bike2 = new Bike("Trailblazer","Urban","Simple speed","2021","The \"Trailblazer\" - a rugged and durable off-road bike perfect for tackling tough terrain and steep inclines.",Arrays.asList(new Picture( "https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/108870/thumb_cube-supreme-hybrid-pro-500.jpg.jpg")),hayRyad,25,4,false,owner2,null,null,true,0.15,0.2,3);
            Bike bike3 = new Bike("Voyager","E-bike","M3L","2021","The \"Voyager\" - a comfortable touring bike with ample storage space and a smooth ride for long distance trips.",Arrays.asList(new Picture( "https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/120785/thumb_blob.jpg")),hayRyad,30,5,false,owner2,null,null,true,0.13,0.2,7);

            Bike bike4 = new Bike("EcoRider","Urban","M3L","2019","The \"EcoRider\" - a fuel-efficient and environmentally friendly bike with a hybrid electric motor.",Arrays.asList(new Picture( "https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/49304/thumb_1619563364-IMG_20200301_134900.jpg")), agdal,10,3,false,owner1,null,null,true,0.1,0.2,9);
            Bike bike5 = new Bike("Cruiser","Urban","Simple speed","2021","The \"Cruiser\" - a classic and stylish bike with a relaxed riding position and retro design.",Arrays.asList(new Picture( "https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/23622/thumb_1619548870-IMG_20190514_204655.jpg")),babAlHad,15,5,false,owner2,null,null,true,0.17,0.2,5);
            Bike bike6 = new Bike("Speedster","E-bike","M3L","2021","The \"Speedster\" - a lightweight and aerodynamic racing bike designed for high speeds and competitive performance.",Arrays.asList(new Picture( "https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/79175/thumb_Schermafbeelding_2021-12-09_om_11.57.23.jpg")),babAlHad,33,4,false,owner2,null,null,true,0.13,0.2,10);

            Bike bike7 = new Bike("Adventurer","Urban","M3L","2019","The \"Adventurer\" - a versatile and capable adventure bike with advanced suspension and off-road capabilities.",Arrays.asList(new Picture( "https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/121282/thumb_blob.jpg")), agdal,7,4,false,owner1,null,null,true,0.11,0.2,18);
            Bike bike8 = new Bike("CityCommuter","Urban","Simple speed","2021","The \"CityCommuter\" - a compact and efficient bike designed for urban riding and navigating through traffic.",Arrays.asList(new Picture( "https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/104671/thumb_1619547401-1548500645-Giant_triple_x_Wit.jpg")),agdal,5,5,false,owner2,null,null,true,0.14,0.2,2);
            Bike bike9 = new Bike("BMX Pro","E-bike","M3L","2021","The \"BMX Pro\" - a durable and high-performance bike built for BMX racing and extreme stunts.",Arrays.asList(new Picture( "https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/48178/thumb_1619562878-5c81375c0a246_IMG_0924.jpg")),hayRyad,23,5,false,owner2,null,null,true,0.13,0.2,17);

            Bike bike10 = new Bike("Canyon Ultimate", "Road", "CF SLX Disc 9.0", "2022", "The \"Canyon Ultimate\" - a high-end road bike designed for speed and comfort.", Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/45182/thumb_1619560186-DSC_3902-min.jpg")), maarif, 54, 11, true, owner1, null, null, false, 0.11, 0.18, 22);


            Bike bike11 = new Bike("Trek Fuel EX", "All terrain", "9.9 X01", "2022", "The \"Trek Fuel EX\" - a high-performance mountain bike for technical terrain and epic adventures.", Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/122965/thumb_blob.jpg")), ainDiab, 18, 12, true, owner2, null, null, true, 0.16, 0.22, 29);
            Bike bike12 = new Bike("Specialized Roubaix", "Road", "Expert", "2021", "The \"Specialized Roubaix\" - a versatile road bike with a comfortable ride and race-winning performance.", Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/57407/thumb_1619568160-IMG_4243.jpg")), elAzzouzia, 56, 22, false, owner1, null, null, false, 0.09, 0.14, 18);
            Bike bike13 = new Bike("Giant TCR", "Road", "Advanced Pro 2", "2022", "The \"Giant TCR\" - a lightweight and stiff road bike designed for racing and climbing.", Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/43337/thumb_1619558218-DSC_3873-min.jpg")), hayCharaf, 52, 11, true, owner2, null, null, true, 0.14, 0.19, 20);
            Bike bike14 = new Bike("Cannondale Synapse", "Road", "Carbon Disc 105", "2021", "The \"Cannondale Synapse\" - a comfortable and versatile road bike for long rides and rough roads.", Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/53640/thumb_1619566085-DSC_3862-min.jpg")), ainChock, 54, 22, false, owner1, null, null, false, 0.1, 0.15, 19);

            Bike bike16 = new Bike("Scott Genius", "All terrain", "900 Ultimate", "2022", "The \"Scott Genius\" - a versatile and high-performance mountain bike for all types of terrain.", Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/115235/thumb_1623769111-IMG_8033.jpg")), hayAlMassar, 21, 11, true, owner2, null, null, true, 0.17, 0.24, 27);
            Bike bike17 = new Bike("Cervelo S5", "Road", "Disc Ultegra Di2", "2021", "The \"Cervelo S5\" - an aero road bike with exceptional speed and handling.", Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/115239/thumb_7BD915C9-5D59-4CB2-9A23-2E6641FA3DDE.jpg")), elAzzouzia, 54, 22, false, owner1, null, null, false, 0.11, 0.16, 19);

            Bike bike18 = new Bike("Trek Checkpoint", "Special", "SL 7", "2022", "The \"Trek Checkpoint\" - a versatile and capable gravel bike for adventure and exploration.", Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/107135/thumb_1626350824-Bzen_Brussels_blue_foto_1.jpg")), ainDiab, 54, 11, true, owner2, null, null, true, 0.13, 0.18, 22);
            Bike bike19 = new Bike("Specialized Stumpjumper", "All terrain", "Expert", "2022", "The \"Specialized Stumpjumper\" - a versatile and high-performance mountain bike for technical trails and enduro racing.", Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/32121/thumb_1619550903-IMG_2305.jpg")), hayCharaf, 19, 6, false, owner1, null, null, false, 0.18, 0.25, 29);

            Bike bike20 = new Bike("Giant Reign", "All terrain", "Advanced Pro 1", "2022", "The \"Giant Reign\" - a high-performance and capable mountain bike for aggressive riding and big drops.", Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/95393/thumb_Photo1.jpg")), ainChock, 17, 7, true, owner2, null, null, true, 0.16, 0.23, 27);


            Bike bike22 = new Bike("Specialized Diverge","Special","Carbon Comp","2021","The \"Specialized Diverge\" - a versatile and capable bike designed for adventure and exploration on any terrain.",Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/94538/thumb_Photo2-1.jpg")), ainDiab, 50, 10, false, owner1, null, null, true, 0.12, 0.18, 18);

            Bike bike23 = new Bike("Cannondale Scalpel","All terrain","Carbon 2","2022","The \"Cannondale Scalpel\" - a high-performance full-suspension mountain bike built for aggressive trail riding and racing.",Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/93698/thumb_Photo1.jpg")), hayCharaf, 40, 8, false, owner2, null, null, true, 0.15, 0.2, 20);

            Bike bike24 = new Bike("Trek Domane","Road","SLR 6","2021","The \"Trek Domane\" - a comfortable and fast road bike with advanced vibration dampening technology for a smooth ride on any road surface.",Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/93697/thumb_Photo2.jpg")), hayAlMassar, 56, 12, false, owner1, null, null, true, 0.11, 0.16, 19);

            Bike bike25 = new Bike("Giant Reign","All terrain","Advanced Pro 29 1","2021","The \"Giant Reign\" - a high-performance enduro mountain bike with advanced suspension and frame geometry for aggressive trail riding and racing.",Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/45200/thumb_1619560197-DSC_3981-min.jpg")), ainChock, 43, 9, false, owner1, null, null, true, 0.14, 0.19, 20);

            Bike bike26 = new Bike("Santa Cruz Tallboy","All terrain","CC X01 Reserve","2022","The \"Santa Cruz Tallboy\" - a lightweight and versatile full-suspension mountain bike designed for fast and efficient trail riding and cross-country racing.",Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/90752/thumb_Photo1.jpg")), elAzzouzia, 49, 11, false, owner2, null, null, true, 0.13, 0.17, 19);

            Bike bike27 = new Bike("Canyon Spectral","All terrain","CF 7","2021","The \"Canyon Spectral\" - a versatile and capable full-suspension mountain bike designed for aggressive trail riding and all-mountain adventures.",Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/90746/thumb_Photo1.jpg")), hayAlMassar, 45, 8, false, owner1, null, null, true, 0.15, 0.2, 20);

            Bike bike28 = new Bike("Scott Addict","Road","RC 15","2021","The \"Scott Addict\" - a lightweight and fast road bike for competitive racing and long endurance rides.",Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/45192/thumb_1619560191-DSC_3919-min.jpg")), ainChock, 54, 14, false, owner2, null, null, true, 0.1, 0.16, 18);

            Bike bike29 = new Bike("Trek Slash","All terrain","9.9 X01 AXS","2022","The \"Trek Slash\" - a high-performance enduro mountain bike with advanced suspension and frame technology for aggressive trail riding and racing.",Arrays.asList(new Picture("https://listnride.s3.eu-central-1.amazonaws.com/uploads/ride_image/image_file/57093/thumb_1619567836-image1.jpg")), elAzzouzia, 47, 10, false, owner1, null, null, true, 0.14, 0.19, 20);//

            userService.addUser(owner1);
            userService.addUser(owner2);
            List<Bike> bikes= Arrays.asList(bike,bike2,bike3,bike4,bike5,bike6,bike7,bike8,bike9,bike10,bike11,bike12,bike13,bike14,bike16,bike17,bike18,bike19,bike20,bike22,bike23,bike24,bike25,bike26,bike27,bike28,bike29);

            bikeService.addAllBikes(bikes);

            mailService.sendVerificationMail("moharbani01@gmail.com","Application Started Successfully","Test Mail Service","Mohamed");




        } ;
    }
}


