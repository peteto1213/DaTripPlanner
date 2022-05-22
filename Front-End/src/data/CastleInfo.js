export default[
    {
        castleId: 1,
        mapLink: "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2264.699921809375!2d-1.708109083963579!3d55.415585825416485!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x487e00e0ed23bc0d%3A0x8783a98b290f641!2z6Zi_5bC85YWL5Z-O5aCh!5e0!3m2!1szh-TW!2suk!4v1651355371043!5m2!1szh-TW!2suk",
        source: process.env.PUBLIC_URL + "/assests/photo/Alnwick.jpg",
        castleName: "Alnwick Castle",
        castleDescription: "Alnwick Castle has over 950 years of history to discover, and the origins of the Castle date back to the Norman period. Since 1309, its story has been intertwined with that of the Percy family, a family with a history as illustrious as the castle’s own",
        price: "15.75 (Castle Concession)",
        link: "https://www.alnwickcastle.com/",
        isClicked: false,
        router: "/castle-alnwick",
        to_do: [
            {id: 1, name: "museums", link: "https://www.alnwickcastle.com/explore/whats-here/museums"},
            {id: 2, name: "capability brown walk", link: "https://www.alnwickcastle.com/explore/whats-here/capabilitywalk"},
            {id: 3, name: "tours, talk & trail", link: "https://www.alnwickcastle.com/explore/whats-here/show-and-tour-times"},
            {id: 4, name: "dragon quests", link: "https://www.alnwickcastle.com/explore/whats-here/artisanscourtyard"}
        ],
        duration: "2hrs 0mins",
        restaurants: [
            {
                id: 1,
                name: "Mumbai Flavours",
                ratings: "4.5 out of 5",
                address: "15 Narrowgate, Alnwick NE66 1JH England",
                service: "Street Food, Indian, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://ichef.bbci.co.uk/news/976/cpsprodpb/8D1D/production/_102952163_de54.jpg"
            },
            {
                id: 2,
                name: "Strawberry Lounge",
                ratings: "5 out of 5",
                address: "20 Narrowgate, Alnwick NE66 1JG England",
                service: "Cafe, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-p/21/5b/92/6c/cakes.jpg"
            },
            {
                id: 3,
                name: "the treehouse restaurant",
                ratings: "4.3 out of 5",
                address: "The Alnwick Garden, Denwick Lane, Alnwick NE66 1YU",
                service: "Dine-in · Drive-through · No delivery",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/17/bc/4b/0e/exterior-of-the-treehouse.jpg"
            },
            {
                id: 4,
                name: "Thai Vibe",
                ratings: "5 out of 5",
                address: "22 Narrowgate, Alnwick NE66 1JG England",
                service: "Dinner, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/13/84/54/d0/img-20180629-wa0006-largejpg.jpg"
            },
            {
                id: 5,
                name: "The Black Swan Pub",
                ratings: "4 out of 5",
                address: "26 Narrowgate, Alnwick NE66 1JG England",
                service: "Breakfast, Lunch, Dinner, Brunch, After-hours, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/02/e9/33/51/the-black-swan.jpg"
            },
            {
                id: 6,
                name: "The Dirty Bottles Taproom, Smokehouse & Inn",
                ratings: "4 out of 5",
                address: "32 Narrowgate, Alnwick NE66 1JG England",
                service: "Lunch, Dinner, Drinks, Brunch, Vegetarian Friendly, Gluten Free Options",
                photo: "https://q-xx.bstatic.com/xdata/images/hotel/840x460/291560676.jpg?k=4652227823d9f117af29a6571df70365a05f2e17ada906cc0ff30fd2ac8e60f8&o="
            },
        ],
        accessibility: "WHEELCHAIR ACCESS, MOTORISED SCOOTER HIRE, ACCESSIBLE PARKING, ACCESSIBLE TOILETS, CARERS",
        accessibilityLink: "https://www.alnwickcastle.com/accessibility"

    },
    {
        castleId: 2,
        source: process.env.PUBLIC_URL + "/assests/photo/Auckland.jpg",
        mapLink: "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d10977.472026555759!2d-1.6745881484626706!3d54.66159212928583!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x487e82382c8ee3d3%3A0xe85f1ebcc3cbec00!2sAuckland%20Castle!5e0!3m2!1szh-TW!2suk!4v1651356192207!5m2!1szh-TW!2suk",
        castleName: "Auckland Castle",
        castleDescription: "Auckland Castle, which is also known as Auckland Palace and to people that live locally as the Bishop's Castle or Bishop's Palace, is located in the town of Bishop Auckland in County Durham, England.",
        price: "14.00 (Adult)",
        link: "https://aucklandproject.org/venues/auckland-castle/",
        isClicked: false,
        router: "/castle-auckland",
        to_do: [
            {id: 1, name: "The Spanish Gallery", link: "https://aucklandproject.org/venues/spanish-gallery/"},
            {id: 2, name: "Mining Art Gallery", link: "https://aucklandproject.org/venues/mining-art-gallery/"},
            {id: 3, name: "Auckland Tower", link: "https://aucklandproject.org/venues/auckland-tower/"},
            {id: 4, name: "Kynren Performance", link: "https://aucklandproject.org/venues/kynren/"}
        ],
        duration: "2hrs 5mins",
        restaurants: [
            {
                id: 1,
                name: "Bishop's Kitchen",
                ratings: "4.5 out of 5",
                address: "Auckland Castle Market Place, Bishop Auckland DL14 7NP England",
                service: "Lunch, Cafe",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/19/a6/32/4e/getlstd-property-photo.jpg"
            },
            {
                id: 2,
                name: "Fifteas Vintage Tearoom",
                ratings: "4.5 out of 5",
                address: "9 Market Place, Bishop Auckland DL14 7NJ England",
                service: "Cafe, British",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/17/b1/60/a7/20190526-155902-largejpg.jpg"
            },
            {
                id: 3,
                name: "Breaking Bread Kitchen & Bakehouse",
                ratings: "4.5 out of 5",
                address: "8 Market Place, Bishop Auckland DL14 7NJ England",
                service: "Dinner, Breakfast, Lunch, Vegetarian Friendly, Gluten Free Options, Vegan Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-p/18/48/a8/21/the-courtyard-is-a-real.jpg"
            },
            {
                id: 4,
                name: "Chang Thai",
                ratings: "4 out of 5",
                address: "7 Market Place, Bishop Auckland DL14 7NJ England",
                service: "Lunch, Dinner, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/0d/c5/f2/cd/changthai-restaurant.jpg"
            },
            {
                id: 5,
                name: "Spudfellas",
                ratings: "5 out of 5",
                address: "68 Fore Bondgate, Bishop Auckland DL14 7PE England",
                service: "British, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/19/51/85/b9/mr-big-stuff.jpg"
            },
            {
                id: 6,
                name: "Cafe Grouchos",
                ratings: "5 out of 5",
                address: "Shop no.4 waterfront, Stanley Stanley Stanley, Stanley England",
                service: "Cafe",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/1b/d2/d6/7f/deluxe-fries-68.jpg"
            },
        ],
        accessibility: "LIMITED WHEELCHAIR ACCESS",
        accessibilityLink: "https://aucklandproject.org/plan-a-visit/accessibility/"

    },
    {
        castleId: 3,
        source: process.env.PUBLIC_URL + "/assests/photo/Bamburgh.jpg",
        mapLink: "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1340.0012219509215!2d-1.711049269801306!3d55.60889750730147!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4880aea65a02c42f%3A0xfc42605b9141c368!2z54-t5Lyv5Z-O5aCh!5e0!3m2!1szh-TW!2suk!4v1651356279641!5m2!1szh-TW!2suk",
        castleName: "Bamburgh Castle",
        castleDescription: "Welcome to Bamburgh Castle, the Kingdom of Northumbria’s epicentre and home of the Armstrong family since 1894 Crowning nine acres of the Great Whin Sill, Bamburgh Castle has stood guard above the Northumberland coast for thousands of years",
        price: "14.10 (Adult)",
        link: "https://www.bamburghcastle.com/",
        isClicked: false,
        router: "/castle-bamburgh",
        to_do: [
            {id: 1, name: "Bamburgh Beach", link: "https://www.tripadvisor.co.uk/Attraction_Review-g504035-d14903135-Reviews-Bamburgh_Beach-Bamburgh_Northumberland_England.html"},
            {id: 2, name: "Grace Darling Museum", link: "https://www.tripadvisor.co.uk/Attraction_Review-g504035-d2173576-Reviews-Grace_Darling_Museum-Bamburgh_Northumberland_England.html"},
            {id: 3, name: "The Northumberland Coast Area", link: "https://www.tripadvisor.co.uk/Attraction_Review-g504051-d7205565-Reviews-The_Northumberland_Coast_Area_of_Outstanding_Natural_Beauty-Seahouses_Northumberl.html"},
            {id: 4, name: "Bamburgh Castle Golf Club", link: "https://www.tripadvisor.co.uk/Attraction_Review-g504035-d7757279-Reviews-Bamburgh_Castle_Golf_Club-Bamburgh_Northumberland_England.html"}
        ],
        duration: "2hrs 10mins",
        restaurants: [
            {
                id: 1,
                name: "The Potted Lobster, Bamburgh",
                ratings: "4.5 out of 5",
                address: "2-3 Lucker Road, Bamburgh NE69 7BS England",
                service: "Seafood, British, Healthy, Lunch, Dinner",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/16/99/49/da/pan-fried-cod-with-garlic.jpg"
            },
            {
                id: 2,
                name: "Wyndenwell",
                ratings: "4.5 out of 5",
                address: "Front Street, Bamburgh NE69 7BJ England",
                service: "Takeout, Seating, Wheelchair Accessible, Drinks",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/18/c2/45/2b/shop.jpg"
            },
            {
                id: 3,
                name: "Copper Kettle Tearoom",
                ratings: "4 out of 5",
                address: "21 Front Street, Bamburgh NE69 7BW England",
                service: "Breakfast, Lunch, Brunch, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/18/4a/54/c1/clayfish-dish.jpg"
            },
            {
                id: 4,
                name: "St Aidan Bistro",
                ratings: "4.5 out of 5",
                address: "The Alnwick Garden, Denwick Lane, Alnwick NE66 1YU",
                service: "European, British, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/08/9c/70/9d/st-aidans-restaurant.jpg"
            },
            {
                id: 5,
                name: "Elan Pizzeria",
                ratings: "4.5 out of 5",
                address: "NE66 1YU 68 Main Street, Seahouses NE68 7TP England",
                service: "Pizza, Contemporary, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/1c/32/a6/6a/caesar.jpg"
            },
            {
                id: 6,
                name: "The Links Restaurant",
                ratings: "4.5 out of 5",
                address: "8 King Street, Seahouses NE68 7XP England",
                service: "Bar, British",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/1a/81/a5/a8/restaurant.jpg"
            },
        ],
        accessibility: "LIMITED",
        accessibilityLink: "https://www.bamburghcastle.com/accessibility-statement/"
    },
    {
        castleId: 4,
        source: process.env.PUBLIC_URL + "/assests/photo/Barnard.jpg",
        mapLink: "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d18514.474422732797!2d-1.938771151912235!3d54.54567282918917!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x487c235f994d8231%3A0xa0e235a2aa664382!2zQmFybmFyZCBDYXN0bGUsIOW3tOe0jeW-t-WfjuWgoQ!5e0!3m2!1szh-TW!2suk!4v1651356343602!5m2!1szh-TW!2suk",
        castleName: "Barnard Castle",
        castleDescription: "Set on a high rock above the River Tees, Barnard Castle takes its name from its 12th century founder, Bernard de Balliol. It was later developed by the Beauchamp family and then passed into the hands of Richard III.",
        price: "6.30 (Concession)",
        link: "https://www.english-heritage.org.uk/visit/places/barnard-castle/",
        isClicked: false,
        router: "/castle-barnard",
        to_do: [
            {id: 1, name: "The Bowes Museum", link: "https://www.tripadvisor.co.uk/Attraction_Review-g190814-d260745-Reviews-The_Bowes_Museum-Barnard_Castle_County_Durham_England.html"},
            {id: 2, name: "Bowlees Visitor Centre", link: "https://www.tripadvisor.co.uk/Attraction_Review-g190814-d4700778-Reviews-Bowlees_Visitor_Centre-Barnard_Castle_County_Durham_England.html"},
            {id: 3, name: "Teesdale Alpacas", link: "https://www.tripadvisor.co.uk/Attraction_Review-g190814-d8471950-Reviews-Teesdale_Alpacas_Open_by_Appointment_Only-Barnard_Castle_County_Durham_England.html"},
            {id: 4, name: "The Witham", link: "https://www.tripadvisor.co.uk/Attraction_Review-g190814-d5571206-Reviews-The_Witham-Barnard_Castle_County_Durham_England.html"}
        ],
        duration: "2hrs 10mins",
        restaurants: [
            {
                id: 1,
                name: "Valentine's Restaurant",
                ratings: "4.5 out of 5",
                address: "11 Galgate, Barnard Castle DL12 8EQ England",
                service: "British, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/1d/00/62/5f/the-pulled-pork-and-angus.jpg"
            },
            {
                id: 2,
                name: "Katie's Traditional Fish and Chips",
                ratings: "5 out of 5",
                address: "15 Horsemarket, Barnard Castle DL12 8LY England",
                service: "Cafe, Fast food, British, Seafood, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/11/01/71/5b/fish-chips-and-mushy.jpg"
            },
            {
                id: 3,
                name: "The Crown at Mickleton",
                ratings: "4.5 out of 5",
                address: "Mickleton, Barnard Castle DL12 0JZ England",
                service: "Bar, British, Pub, Contemporary, Gastropub, Vegetarian Friendly, Gluten Free Options, Vegan Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/05/b2/ad/6d/the-crown-at-mickleton.jpg"
            },
            {
                id: 4,
                name: "No. 3 Coffee Bar & Grill",
                ratings: "4.5 out of 5",
                address: "3 Galgate, Barnard Castle DL12 8EQ England",
                service: "Cafe, British, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/05/20/cf/ae/no-3.jpg"
            },
            {
                id: 5,
                name: "Cross Lanes Organic Farm",
                ratings: "4.5 out of 5",
                address: "The Cottage Cross Lanes Brignall, Barnard Castle DL12 9SL England",
                service: "Cafe, British, Vegetarian Friendly, Vegan Options, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-f/16/7b/da/b7/20190215-131444-largejpg.jpg"
            },
            {
                id: 6,
                name: "The Fox & Hounds Pub",
                ratings: "4.5 out of 5",
                address: "Cotherstone, Cotherstone, Barnard Castle DL12 9PF England",
                service: "Bar, British, Pub, Vegetarian Friendly, Gluten Free Options",
                photo: "https://media-cdn.tripadvisor.com/media/photo-s/0f/c4/df/c5/bacon-salad.jpg"
            },
        ],
        accessibility: "ACCESSIBILE TOILETS, ASSISTANCE DOGS WELCOME, BRAILLE GUIDES",
        accessibilityLink: "https://www.english-heritage.org.uk/visit/places/barnard-castle/access/"
    }
]