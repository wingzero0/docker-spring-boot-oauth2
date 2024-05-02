import request from 'axios'

export const getRoleList = async (pageNumber: number, pageSize: number): Promise<{
  registeredClientId: string,
  username: string,
  rolename: string,
}[]> => {
  if (import.meta.env.MODE === 'mock') {
    console.log("in mock mode");
    const mockRole = {
      registeredClientId: "message_client_ui",
      username: "username",
      rolename: "role name",
    };
    return [mockRole];
  }
  try {
    const uri: string = '/api/role?pageNumber=' + pageNumber + "&pageSize=" + pageSize;
    const response = await request.get(uri, {});
    console.log(response);
    console.log(response.data);
  } catch (error) {
    console.log("got error")
    console.log(error)
    return [];
  }
  return [];
}