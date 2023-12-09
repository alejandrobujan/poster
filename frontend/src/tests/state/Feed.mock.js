export const feedMock = {

	app: {
		error: null,
		firstSearch: false
	},
	catalog: {
		categories: [
            {id: 1, name: "Meals"},
            {id: 2, name: "Motor"},
            {id: 3, name: "Home"},
            {id: 4, name: "Toys"},
            {id: 5, name: "Tech"},
            {id: 6, name: "Leisure"}
        ],
		postSearch: {
            criteria: {
                page: 0
            },
            result: {
                items: [],
                existMoreItems: false
            }
        },
		searchParams: {
			keywords: "",
			filters: {
				categoryId: "",
				type: "",
				price: {
					gte: 0,
					lte: 1000000
				},
				date: "",
				expired: false,
				sortParam: "creationDate",
				sortOrder: "DESC"
			}
		},
		requestRefresh: true
	}
}