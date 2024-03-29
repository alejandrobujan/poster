export const updatePostSecondConditionMock = {
	users: {
		user: {
            id: 2,
            userName: "Pepe",
            firstName: "Pepe",
            lastName: "Pepe",
            email: "Pepe@pepe.com",
            avatar: null
		}
	},
	post: {
		post: {
			id: 1,
			title: "Coupon",
			description: "Buena oferta",
			url: "https://cupon",
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
			creationDate: null,
			positiveRatings: 0,
			negativeRatings: 0,
			expirationDate: 1670409960000,
			type: "Coupon",
			properties: {
				code: "12345"
			},
			validationDate: 1670409960000
		}
	},
    catalog: {
		categories: [
			{ id: 1, name: "Meals" },
			{ id: 2, name: "Motor" },
			{ id: 3, name: "Home" },
			{ id: 4, name: "Toys" },
			{ id: 5, name: "Tech" },
			{ id: 6, name: "Leisure" }
		]
	}
}