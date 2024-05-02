import request from 'axios'

export const getAppList = async (pageNumber: number, pageSize: number): Promise<string[]> => {
  try {
    const uri: string = '/api/app?pageNumber=' + pageNumber + "&pageSize=" + pageSize;
    const response = await request.get(uri, {});
    console.log(response);
    const clientId = response.data.content.map((app: { clientId: string; })=>app.clientId);
    console.log(clientId);
  } catch (error) {
    console.log("got error")
    console.log(error)
    return ["error"];
  }
  return ["not implement"];
}