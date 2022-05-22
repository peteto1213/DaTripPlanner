export default[
    {
        id: 1001,
        routeId: "",
        busTicketID:"",
        stops:[
            {id: 2001, name: "Newcastle central station", time: "16mins", operator: "404/LNER", type: "train", price: 19.5},
            {id: 2002, name: "Durham", time: "30mins", operator: "X12/bus operator", type:"bus", price: 10.5},
        ],
        destination: "Alnwick Castle",
        totalTime: "55mins",
        isClicked: false,
        isSelected: false,
    },

    //resource.data.
    {
        id: 1002,
        stops:[
            {id: 2003, name: "Newcastle central station", time: "16mins", operator: "LNER", type: "train", price: 19.5},
            {id: 2004, name: "Durham", time: "30mins", operator: "bus operator", type:"bus", price: 10.5},
        ],
        destination: "Auckland Castle",
        totalTime: "55mins",
        isClicked: false,
        isSelected: false,
    },
    // {
    //     id: 1003,
    //     stops:[
    //         {name: "Eldon Square", time: "15mins"},
    //         {name: "stop1", time: "30mins"},
    //         {name: "stop2", time: "10mins"}
    //     ],
    //     destination: "Alnwick Castle",
    //     total_time: "55mins",
    //     isClicked: false,
    //     isSelected: false,
    // },
    // {
    //     id: 1004,
    //     stops:[
    //         {name: "Eldon Square", time: "15mins"},
    //         {name: "stop1", time: "30mins"},
    //         {name: "stop2", time: "10mins"}
    //     ],
    //     destination: "Alnwick Castle",
    //     total_time: "55mins",
    //     isClicked: false,
    //     isSelected: false,
    // }
]