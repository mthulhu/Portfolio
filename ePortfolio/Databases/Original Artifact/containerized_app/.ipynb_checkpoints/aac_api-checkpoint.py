from pymongo import MongoClient
from bson.objectid import ObjectId

class AnimalShelter(object):
    def __init__(self):
        # Initializing the MongoClient
        USER = 'aacuser'
        PASS = 'password'
        HOST = 'nv-desktop-services.apporto.com'
        PORT = 32796
        DB = 'AAC'
        COL = 'animals'

        self.client = MongoClient(f'mongodb://{USER}:{PASS}@{HOST}:{PORT}')
        self.database = self.client[DB]
        self.collection = self.database[COL]

    def create(self, data):
        if data is not None:
            result = self.database.animals.insert_one(data)  
            return result.acknowledged  # Returns True if the insert was successful
        else:
            raise Exception("Nothing to save, because data parameter is empty")
    
    def read(self, query):
        if query is not None:
            try:
                result = self.database.animals.find(query)  
                return list(result)  
            except Exception as e:
                print(f"An error occurred while reading: {e}")
                return []  # Return an empty list on error
        else:
            raise Exception("Query parameter is empty")
    
    def update(self, query, update_data):
        if query is not None and update_data is not None:
            try:
                result = self.database.animals.update_many(query, {"$set": update_data})  
                return result.modified_count  # Return the number of documents updated
            except Exception as e:
                print(f"An error occurred while updating: {e}")
                return 0  # Return 0 on error
        else:
            raise Exception("Query and update_data parameters cannot be empty")
    
    def delete(self, query):
        if query is not None:
            try:
                result = self.database.animals.delete_many(query)  
                return result.deleted_count  # Return the number of documents deleted
            except Exception as e:
                print(f"An error occurred while deleting: {e}")
                return 0  # Return 0 on error
        else:
            raise Exception("Query parameter is empty")