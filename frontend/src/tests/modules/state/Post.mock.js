export const postMock = {
    users: {
        user: {
            serviceToken: "923u2344823903480923",
            userDto: {
                id: 1,
                userName: "Pepe",
                password: "Pepe",
                firstName: "Pepe",
                lastName: "Pepe",
                email: "Pepe@pepe.com",
                avatar: null
            }
        }
    },
    post:{
        post: {
            id: 1,
            title: "Coupon",
            description: "Buena oferta",
            url: "http://cupon",
            price: 10,
            categoryDto: {
                id: 1,
                name: "Belleza"
            },
            userSummaryDto: {
                id: 1,
                userName: "Pepe",
                firstName: "Pepe",
                lastName: "Pepe",
                avatar: null
            },
            images: [],
            creationDate: 1384723847238,
            positiveRatings: 0,
            negativeRatings: 0,
            expired: false,
            type: "Coupon",
            properties: {
                code: "12345"
            }
        }
    },
    comment: {
        comments: {
            page: 0,
            elems: {
                items: [],
                existMoreItems: false
            }
        }
    }
}