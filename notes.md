# Schema
- inserting into avaliabilities or booking a start and end date with the same price will not merge the two
- we assume booking interval is not inclusive therefore we can have liabilities and booking that start and end on the same day
- The only notion of current date time in this schema is when we calculate the age. This schema makes no attempt to reference
  current time so its entirely possible to booked a listing in the past. This is because current time introduces too much
  complexity as we would need to update the database daily to enforce our current constraint