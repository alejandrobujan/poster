export const searchBarMock = {

	app: {
		error: null,
		firstSearch: false
	},
	catalog: {
		categories: null,
		postSearch: null,
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
		}
	}
}