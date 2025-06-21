db = db.getSiblingDB("AAC");

db.animals.insertOne({
	rec_num: 7,
	age_upon_outcome: "7 years",
	animal_id: "A123456",
	animal_type: "Cat",
	breed: "Sphynx",
	color: "Black",
	date_of_birth: "2015-01-27",
	datetime: "2022-01-28 14:15:00",
	monthyear: "2022-01-28T14:15:00",
	name: "Calcifer",
	outcome_subtype: "Adoption",
	outcome_type: "Adopted",
	sex_upon_outcome: "Neutered Male",
	location_lat: 40.712776,
	location_long: -74.005974,
	age_upon_outcome_in_weeks: 364
});
