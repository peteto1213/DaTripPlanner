class FooterData{

    getAboutUs(){
        let description = "We are a group of students working on a project to assist tourists to plan a day trip to the 4 main castles around Newcastle" +
            "\n" + "Hope you will enjoy with the functionalities of this web application. Please feel free to leave a message to us via the contact session, Cheers!"
        return description;
    }

    getMembers(){
        let membersArray =
            [
                {
                    id: 1,
                    name: "Kevin",
                    profileLink: "https://www.linkedin.com/in/kaiwen-zuo-85061423a/"
                },
                {
                    id: 2,
                    name: "Lei",
                    profileLink: "https://www.linkedin.com/in/%E7%A3%8A-%E9%99%88-1a060123a"
                },
                {
                    id: 3,
                    name: "Lewis",
                    profileLink: "https://www.linkedin.com/in/lewis-bush-307b6511b/"
                },
                {
                    id: 4,
                    name: "Pete",
                    profileLink: "https://www.linkedin.com/in/pete-to-301b29234"
                },
                {
                    id: 5,
                    name: "Weidong",
                    profileLink: "https://www.linkedin.com/in/wedding-zhang-ab2465224"
                },
            ]
        return membersArray;
    }

    getSocialMediaLinks(){
        let socialMediaLinks =
            [
                {
                    id: 1,
                    name: "facebook",
                    mediaLink: "https://www.facebook.com/DaTrip-Planner-110973878275410"
                },
                {
                    id: 2,
                    name: "instagram",
                    mediaLink: "https://www.instagram.com/datripplanner2022/"
                },
                {
                    id: 3,
                    name: "twitter",
                    mediaLink: "https://twitter.com/DatripPlanner"
                },
                {
                    id: 5,
                    name: "Pinterest",
                    mediaLink: "https://www.pinterest.com/datripplanner"
                },
            ];
        return socialMediaLinks;
    }
}

export default new FooterData();
